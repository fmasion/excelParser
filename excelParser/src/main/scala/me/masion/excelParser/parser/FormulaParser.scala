package me.masion.excelParser.parser

import me.masion.excelParser.models.ast.ASTNode
import me.masion.excelParser.models.functionast._
import me.masion.excelParser.models.{Primitive, Str, Bool}
import org.parboiled2._

/**
 * Created by fred on 27/03/15.
 */
class FormulaParser(val input: ParserInput) extends Parser with CellsParser with NumericParser with DateParser with ParserUtils {

  def InputLine =  rule { OptionalSpaces ~ Input ~ OptionalSpaces ~ EOI }

  def Input = rule { Formula | Primitive | ArithmeticExpression }

  def Expression:Rule1[ASTNode] = rule { Parens | Function | ComparisonFunction | Cell | Primitive }

  def Formula = rule { "=" ~ OptionalSpaces ~ Expression ~ OptionalSpaces ~ EOI }

  def Primitive:Rule1[Primitive] = rule { DateInput | Number }

  def Function = rule { BoolFunction  }

//  def ConcatenationOperator = rule { OptionalSpaces ~ '&' ~ OptionalSpaces }

//  def PercentageOperator = rule { OptionalSpaces ~ '%' ~ OptionalSpaces }


  //BOOLEAN
  def BoolFunction: Rule1[LogicalFunction] = rule { AND | NOT | OR | IF }
  def NOT = rule { ignoreCase("not") ~ LParen ~ Expression ~ RParen ~> Not}
  def AND = rule { ignoreCase("and") ~ LParen ~ oneOrMore(Expression).separatedBy(UNION_OPERATOR) ~ RParen ~> And }
  def OR = rule { ignoreCase("or") ~ LParen ~ oneOrMore(Expression).separatedBy(UNION_OPERATOR) ~ RParen ~> Or }
  def IF = rule { ignoreCase("if") ~ LParen ~ Expression ~ UNION_OPERATOR ~ Expression ~ UNION_OPERATOR ~ Expression ~ RParen ~> ((c:ASTNode, l:ASTNode, r:ASTNode) => If(c,l,r) ) }

  def False: Rule1[Bool] = rule { ignoreCase("false") ~ push(Bool(false)) }
  def True: Rule1[Bool] = rule { ignoreCase("true") ~ push(Bool(true)) }

  def ComparisonFunction: Rule1[ASTNode] = rule {  (Cell | Primitive | Function) ~  capture(ComparisonOperator) ~ Expression ~> ((l:ASTNode,o:String,r:ASTNode) => LogicalFunction.from(l,o.trim,r)) }
  def ComparisonOperator = rule { OptionalSpaces ~ ">=" | "<=" | "<" | ">" | NotEqual | "=" ~ OptionalSpaces }
  def NotEqual = rule { "<>" | "!=" }

  //ARITHMETIC
  def ExpressionOperator = rule { OptionalSpaces ~ '*' | '/' ~ OptionalSpaces }
  def ExponentiationOperator = rule { OptionalSpaces ~ '^' ~ OptionalSpaces }
  def TermOperator = rule { OptionalSpaces ~ '+' | '-' ~ OptionalSpaces }

  def ArithmeticExpression = rule {
    Term ~ zeroOrMore(
      capture(TermOperator) ~ Term ~> ((lhs:ASTNode, op, rhs:ASTNode) => ArithmeticFunction.from(lhs, op.trim,rhs)))
  }
  def Term = rule {
    Expression ~ zeroOrMore(
      capture(ExpressionOperator | ExponentiationOperator) ~ Expression ~> ((lhs:ASTNode, op:String, rhs:ASTNode) => ArithmeticFunction.from(lhs, op.trim,rhs)))
  }
//  def Factor = rule { Expression | Parens }
  def Parens = rule { LParen ~ Expression ~ RParen }

}
