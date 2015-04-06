package me.masion.front.model.spreadsheet

import me.masion.excelParser.models.cellsReferences.CellRef
import me.masion.excelParser.models.{InvalidFormula, ParseException, Str}
import me.masion.excelParser.parser.FormulaParser
import org.parboiled2.ParseError
import rx._

import scala.annotation.tailrec
import scala.util.{Failure, Success}

/**
 * Created by fred on 02/04/15.
 */
case class Sheet(internalGrid: Var[Map[CellRef, Cell]]= Var(Map.empty), internalRows: Var[Map[Int, Row]]= Var(Map.empty), internalColumns: Var[Map[Int, Column]]= Var(Map.empty)) {

  def grid(cellRef: CellRef) = internalGrid().getOrElse(cellRef, Cell.DEFAULT)
  def row(i: Int) = internalRows().getOrElse(i, Row.default)
  def column(i: Int) = internalColumns().getOrElse(i, Column.default)

  //Give the top or left posistion of a row/column based on precedant row/column size and visibility
  def rowTop(pos:Int) = Iterator.from(1).map( i => row(i) ).filterNot(_.hidden).take(pos -1).map(_.height).sum
  def colLeft(pos:Int) = Iterator.from(1).map( i => column(i) ).filterNot(_.hidden).take(pos -1).map(_.width).sum

  //Give column or row number based on the srollPosition
  def rowNum(scrollPos:Int) = {
    def iter = Iterator.from(1).map( i => row(i)).filterNot(_.hidden).map(_.height)
    sumUntil(1,0)(iter, _>=scrollPos )
  }

  def colNum(scrollPos:Int) = {
    def iter = Iterator.from(1).map( i => column(i)).filterNot(_.hidden).map(_.width)
    sumUntil(1,0)(iter, _>=scrollPos )
  }

  @tailrec
  private def sumUntil(acc:(Int,Int))(list: Iterator[Int], endCondition: Int => Boolean):Int = {
    if (list.hasNext) {
      val next = list.next()
      if (!endCondition(next + acc._2)) {
        sumUntil(acc._1 + 1, next + acc._2)(list, endCondition)
      } else {
        acc._1
      }
    } else {
      acc._1
    }
  }

  def update(cellRef:CellRef, input:String) = {
    grid(cellRef) match{
      case Cell.DEFAULT => internalGrid() = internalGrid() + (cellRef -> Cell(Var(input)))
      case cell => cell.input() =input
    }

  }

}
