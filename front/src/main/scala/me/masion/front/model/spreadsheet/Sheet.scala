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
case class Sheet(internalGrid: Var[Map[CellRef, Cell]]= Var(Map.empty), internalLines: Var[Map[Int, Line]]= Var(Map.empty), internalColumn: Var[Map[Int, Column]]= Var(Map.empty)) {


  def grid(cellRef: CellRef) = Rx {internalGrid().getOrElse(cellRef, EmptyCell)}

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


