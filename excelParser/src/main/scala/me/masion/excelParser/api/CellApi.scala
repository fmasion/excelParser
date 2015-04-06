package me.masion.excelParser.api

import me.masion.excelParser.models._
import me.masion.excelParser.models.ast.ASTNode
import me.masion.excelParser.parser.FormulaParser
import org.parboiled2.ParseError
import rx._

import scala.util.{Failure, Success}

/**
 * Created by fred on 05/04/15.
 */
trait CellApi {

  val errorTip:Var[ParsingError] =Var(Nope)

  def value: Rx[Either[EvaluationError, Primitive]] = Rx { evalutorDelegate(ast()) }

  def ast:Rx[ASTNode] = Rx {
    val parser = new FormulaParser(input())
    parser.InputLine.run() match{
      case Success(ast) => {
        this.errorTip() = Nope
        ast
      }
      case Failure(b: ParseError) => {
        val errorTipInfo: String = parser.formatError(b)
        this.errorTip() = InvalidFormula(errorTipInfo)
        Str(input())
      }
      case Failure(b: Throwable) => {
        this.errorTip() = ParseException(b.getMessage)
        Str(input())
      }
    }
  }

  // to implement
  def input:Var[String]
  // inject the concerte Evaluator impl
  def evalutorDelegate: (ASTNode) => Either[EvaluationError, Primitive]

}
