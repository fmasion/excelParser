package me.masion.excelParser.models.functionast

import me.masion.excelParser.models.Bool
import me.masion.excelParser.models.ast.ASTNode

/**
 * Created by fred on 29/03/15.
 */
trait ArithmeticFunction extends ASTNode
case class Add(lhs:ASTNode, rhs:ASTNode) extends ArithmeticFunction
case class Substract(lhs:ASTNode, rhs:ASTNode) extends ArithmeticFunction
case class Mulitiply(lhs:ASTNode, rhs:ASTNode) extends ArithmeticFunction
case class Divide(lhs:ASTNode, rhs:ASTNode) extends ArithmeticFunction
case class Exponant(lhs:ASTNode, rhs:ASTNode) extends ArithmeticFunction
case class Modulo(lhs:ASTNode, rhs:ASTNode) extends ArithmeticFunction

object ArithmeticFunction{
  def from(lhs:ASTNode, op:String, rhs:ASTNode):ASTNode = op match {
    case "*"  => Mulitiply(lhs, rhs)
    case "/"  => Divide(lhs, rhs)
    case "^" => Exponant(lhs, rhs)
    case "+" => Add(lhs, rhs)
    case "-"  => Substract(lhs, rhs)
    case _ => throw new IllegalArgumentException("BAD ARITHMETIC OPERATOR")
  }
}

