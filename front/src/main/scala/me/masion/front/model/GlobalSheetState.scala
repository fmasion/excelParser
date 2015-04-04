package me.masion.front.model

import me.masion.excelParser.models.cellsReferences.CellRef
import me.masion.front.model.spreadsheet.Sheet
import org.scalajs.jquery.jQuery
import org.scalajs.dom
import rx._

/**
 * Created by fred on 03/04/15.
 */
case class Point(height:Double, width:Double)

object GlobalSheetState{

  val documentSize:Var[Point] = Var(Point(0,0))
  val scrollPosition:Var[Point] = Var(Point(0,0))

  //Default Params
  val defaultColWidth = 50
  val defaultRowHeight = 23


  val currentSheet = Var(Sheet())
  // diplayed cell area
  val rowDisplayRange = Var(1 to 50)
  val colDisplayRange = Var(1 to 50)

  val currentCell: Var[Option[CellRef]]= Var(None)


  def currentCol = Rx { currentCell().map(_.col)}
  def currentRow = Rx { currentCell().map(_.row)}
  def cellSelected = Rx { currentCell().isDefined }



  def setCurrentSheet(sheet:Sheet) = currentSheet() = sheet






}