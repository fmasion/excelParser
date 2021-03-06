package me.masion.excelParser.models

/**
 * Created by fred on 02/04/15.
 */
trait ParsingError{
  def msg:String
}
case class InvalidFormula(msg:String) extends ParsingError
case class ParseException(msg:String) extends ParsingError
case object Nope extends ParsingError{
  val msg = ""
}

trait EvaluationError{
  def msg:String
}
case class EvaluationTypeError(msg:String) extends EvaluationError
