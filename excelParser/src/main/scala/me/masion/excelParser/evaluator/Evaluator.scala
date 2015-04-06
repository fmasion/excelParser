package me.masion.excelParser.evaluator

import me.masion.excelParser.api.CellProvider
import me.masion.excelParser.models.cellsReferences.CellRef
import me.masion.excelParser.models._
import me.masion.excelParser.models.ast.ASTNode
import me.masion.excelParser.models.functionast._

import scala.util.Try


class EvaluationException(msg: String) extends RuntimeException(msg)
/**
 * Created by fred on 05/04/15.
 */
trait Evaluator extends CellProvider {
  import math._

  def evaluate(ast:ASTNode):Either[EvaluationError, Primitive] = try {
    Right(runEvaluate(ast))
  }catch {
    case e: EvaluationException => Left(EvaluationTypeError(e.getMessage))
  }

    private def runEvaluate(ast:ASTNode):Primitive = {
    import me.masion.excelParser.models.Numeric

    ast match {
      case p:Primitive          => p
      case c : CellRef          => this.cellRefToCell(c:CellRef).value() match{
        case Right(p)   => p
        case Left(e)    => throw new EvaluationException(e.msg)
      }

      // Arithmetic
      case Multiply(lhs, rhs)   => Numeric(runEvaluate(lhs).toDouble * runEvaluate(rhs).toDouble)
      case Divide(lhs, rhs)     => Numeric(runEvaluate(lhs).toDouble / runEvaluate(rhs).toDouble)
      case Exponant(lhs, rhs)   => Numeric(pow(runEvaluate(lhs).toDouble, runEvaluate(rhs).toDouble))
      case Add(lhs, rhs)        => Numeric(runEvaluate(lhs).toDouble + runEvaluate(rhs).toDouble)
      case Substract(lhs, rhs)  => Numeric(runEvaluate(lhs).toDouble - runEvaluate(rhs).toDouble)
      case Modulo(lhs, rhs)     => Numeric(runEvaluate(lhs).toDouble % runEvaluate(rhs).toDouble)

      //Logical
      case NotEqual(lhs, rhs)   => Bool(runEvaluate(lhs) != runEvaluate(rhs))
      case Greater(lhs, rhs)    => Bool(runEvaluate(lhs) > runEvaluate(rhs))
      case Lower(lhs, rhs)      => Bool(runEvaluate(lhs) < runEvaluate(rhs))
      case GreaterEq(lhs, rhs)  => Bool(runEvaluate(lhs) >= runEvaluate(rhs))
      case LowerEq(lhs, rhs)    => Bool(runEvaluate(lhs) <= runEvaluate(rhs))
      case Equal(lhs, rhs)      => Bool(runEvaluate(lhs) == runEvaluate(rhs))

      case And(list)            => Bool(list.map(runEvaluate).map(_.toBoolean).forall((e)=> e))
      case Or(list)             => Bool(list.map(runEvaluate).map(_.toBoolean).exists((e)=> e))
      case Not(node)            => Bool(!runEvaluate(node).toBoolean)

      case If(c,t,f)            => if(runEvaluate(c).toBoolean) runEvaluate(t) else runEvaluate(f)



    }
  }
  


}
