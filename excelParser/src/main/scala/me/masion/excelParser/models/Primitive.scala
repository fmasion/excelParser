package me.masion.excelParser.models

import me.masion.excelParser.evaluator.EvaluationException
import me.masion.excelParser.models.ast.ASTNode
import me.masion.excelParser.models.functionast.{ArithmeticFunction, LogicalFunction}
import me.masion.excelParser.models.utils.DateTime

/**
 * Created by fred on 27/03/15.
 */
sealed trait Primitive extends ASTNode with Ordered[Primitive]{
  def toDouble:Double
  def toBoolean:Boolean
  def toDate:DateTime
  def toString:String
}
case class Numeric(value: Double) extends Primitive with ArithmeticFunction {
  override def toDouble: Double = value
  override def toBoolean: Boolean = !(value == 0)
  override def toDate: DateTime = DateTime.fromDouble(value)
  override def toString: String = value.toString
  override def compare(that: Primitive): Int = value.compareTo(that.toDouble)
}
case class Bool(value: Boolean) extends Primitive with LogicalFunction with ArithmeticFunction {
  override def toDouble: Double = if(value) 1 else 0
  override def toBoolean: Boolean = value
  override def toDate: DateTime = throw new EvaluationException("can't change Boolean to Date")
  override def toString: String = if(value) "TRUE" else "FALSE"
  override def compare(that: Primitive): Int = value.compareTo(that.toBoolean)
}
case class Str(value: String) extends Primitive with ArithmeticFunction {
  override def toDouble: Double = throw new EvaluationException("can't change Boolean to Date")
  override def toBoolean: Boolean = value.equalsIgnoreCase("true")
  override def toDate: DateTime = throw new EvaluationException("can't change Boolean to Date")
  override def toString: String = value
  override def compare(that: Primitive): Int = value.compareTo(that.toString)
}
case class Dat(value: DateTime) extends Primitive with ArithmeticFunction {
  override def toDouble: Double = value.toDouble
  override def toBoolean: Boolean = value.toDouble > DateTime(1900,1,1).toDouble
  override def toDate: DateTime = value
  override def toString: String = value.toIsoDateString
  override def compare(that: Primitive): Int = value.toDouble.compareTo(that.toDouble)
}
case object EmptyPrimitive extends Primitive with ArithmeticFunction {
  override def toDouble: Double = 0
  override def toBoolean: Boolean = throw new EvaluationException("can't change Boolean to Date")
  override def toDate: DateTime = throw new EvaluationException("can't change Boolean to Date")
  override def toString: String = ""
  override def compare(that: Primitive): Int = -1
}
