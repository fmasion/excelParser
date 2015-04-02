package me.masion.excelParser.models.spreadsheet

import me.masion.excelParser.models.{ParseException, InvalidFormula, Str, EmptyPrimitive}
import me.masion.excelParser.models.cellsReferences.CellRef
import me.masion.excelParser.parser.FormulaParser
import org.parboiled2.ParseError

import scala.util.{Failure, Success}

/**
 * Created by fred on 02/04/15.
 */
case class Sheet(internalGrid: Map[CellRef, Cell]= Map.empty, internalLines: Map[Int, Line]= Map.empty, internalcolumn: Map[Int, Column]= Map.empty) {



  def lineCells(row:Int) = internalGrid.filter(entry => entry._1.row == row).map(_._2)
  def columnCells(col:Int) = internalGrid.filter(entry => entry._1.col == col).map(_._2)

  def grid(cellRef: CellRef) = internalGrid.getOrElse(cellRef, EmptyCell)

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
    internalGrid + (cellRef -> cell)
  }

}


