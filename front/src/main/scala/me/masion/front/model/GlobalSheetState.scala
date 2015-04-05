package me.masion.front.model

import me.masion.excelParser.models.cellsReferences.CellRef
import me.masion.front.model.spreadsheet.{Cell, Sheet}
import org.scalajs.dom.MouseEvent
import org.scalajs.dom.raw.HTMLDivElement
import org.scalajs.jquery.jQuery
import org.scalajs.dom
import rx._

/**
 * Created by fred on 03/04/15.
 */
case class Point(x:Double, y:Double)

object GlobalSheetState{

  // tableView size
  val documentSize:Var[Point] = Var(Point(0,0))
  val scrollPosition:Var[Point] = Var(Point(0,0))

  //Default Params
  val defaultColWidth = 100
  val defaultRowHeight = 23


  val currentSheet = Var(Sheet())
  // diplayed cell area
  val rowDisplayRange = Var(1 to 50)
  val colDisplayRange = Var(1 to 50)
  val displayOffset = Rx{Point(colDisplayRange().head -1, rowDisplayRange().head -1)}

  val editMode:Var[Boolean] = Var(false)
  val currentCellDiv = Var(Point(0, 0))
  val currentCellRef: Rx[Option[CellRef]] = Rx{ currentCellDiv() match{
    case Point(0,0) => None
    case Point(x,y) => Some(CellRef((x + displayOffset().x).toLong, (y.toLong + displayOffset().y).toLong))
  }}
  val currentCell: Rx[Option[Cell]]= Rx{ currentCellRef().map(cr => GlobalSheetState.currentSheet().grid(cr))}

  def currentCol = Rx { currentCellRef().map(_.col)}
  def currentRow = Rx { currentCellRef().map(_.row)}
  def cellSelected = Rx { currentCell().isDefined }





  def setCurrentSheet(sheet:Sheet) = currentSheet() = sheet

  ///

  def cellClick(col:Int,row:Int)( e:MouseEvent)={
    currentCellDiv() = (Point(col,row))
  }

  def cellDblClick(col:Int,row:Int)( e:MouseEvent)={
    currentCellDiv() = (Point(col,row))
    editMode() = true
  }






}