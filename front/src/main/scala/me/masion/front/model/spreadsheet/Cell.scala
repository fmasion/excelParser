package me.masion.front.model.spreadsheet

import me.masion.excelParser.api.CellApi
import me.masion.excelParser.models.ast.ASTNode
import me.masion.excelParser.models._
import me.masion.excelParser.parser.FormulaParser
import me.masion.front.model.GlobalSheetState
import org.parboiled2.ParseError
import rx._

import scala.util.{Failure, Success}

/**
 * Created by fred on 02/04/15.
 */
case class Cell(input:Var[String], override val errorTip: Var[ParsingError] = Var(Nope), rowSpan:Int=1, colSpan:Int=1) extends CellApi {

  lazy val isSelected = Rx{ GlobalSheetState.currentCell().map(_ == this).getOrElse(false)   }
  // inject the concerte Evaluator impl
  override def evalutorDelegate: (ASTNode) => Either[EvaluationError, Primitive] = GlobalSheetState.evaluate(_)

  override def toString = s"Cell(input: ${input()}, errorTip: ${errorTip()}, rowSpan: ${rowSpan}, colSpan: ${colSpan}, value: ${value()}"
}
