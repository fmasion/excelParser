package me.masion.front.model.spreadsheet

import me.masion.excelParser.models.cellsReferences.CellRef
import me.masion.excelParser.models.{InvalidFormula, ParseException, Str}
import me.masion.excelParser.parser.FormulaParser
import org.parboiled2.ParseError
import rx._

import scala.util.{Failure, Success}

/**
 * Created by fred on 02/04/15.
 */
case class Sheet(internalGrid: Var[Map[CellRef, Cell]]= Var(Map.empty), internalRows: Var[Map[Int, Row]]= Var(Map.empty), internalColumns: Var[Map[Int, Column]]= Var(Map.empty)) {


  def grid(cellRef: CellRef) = Rx {internalGrid().getOrElse(cellRef, EmptyCell)}
  def row(i: Int) = internalRows().getOrElse(i, Row.default)
  def column(i: Int) = internalColumns().getOrElse(i, Column.default)

  def rowTop(pos:Int) = Iterator.from(1).map( i => row(i) ).filterNot(_.hidden).take(pos).map(_.height).sum
  def colLeft(pos:Int) = Iterator.from(1).map( i => column(i) ).filterNot(_.hidden).take(pos).map(_.width).sum


  def update(cellRef:CellRef, input:String) = {
    val parser = new FormulaParser(input)
    val cell = parser.InputLine.run() match{
      case Success(ast) => Cell(input, ast)
      case Failure(b: ParseError) => {
        val errorTip = parser.formatError(b)
        Cell(input, Str(input), InvalidFormula(errorTip) )
      }
      case Failure(b: Throwable) => {
        Cell(input, Str(input), ParseException(b.getMessage) )
      }
    }
    internalGrid() = internalGrid() + (cellRef -> cell)
  }

}
