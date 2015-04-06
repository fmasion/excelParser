package me.masion.front.model

import me.masion.excelParser.evaluator.Evaluator
import me.masion.excelParser.models.cellsReferences.{CellRangeRef, CellAreaRef, CellRef}
import me.masion.excelParser.api.{CellApi, CellProvider}
import me.masion.front.model.spreadsheet.{Cell, Sheet}
import org.scalajs.dom.MouseEvent

import rx._

import scala.scalajs.js.annotation.{JSExportAll, JSExport}

/**
 * Created by fred on 03/04/15.
 */
case class Point(x:Double, y:Double)

object GlobalSheetState extends CellProvider with Evaluator {

  val displayMode = Var(1) // 1/2/3 result/

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
    case p if(p.x >0 || p.y >0) => Some(pointToCellRef(p))
    case Point(0,0) => None
  }}
  val currentCell: Rx[Option[Cell]]= Rx{ currentCellRef().map(cellRefToCell(_))}

  def pointToCellRef(point:Point) = CellRef((point.x + displayOffset().x).toLong, (point.y + displayOffset().y).toLong)
  override def cellRefToCell(cellRef:CellRef) = GlobalSheetState.currentSheet().grid(cellRef)
  def pointToCell(p:Point) = cellRefToCell(pointToCellRef(p))
  override def cellAreaToCells(cellAreaRef: CellAreaRef): Seq[Cell] = cellAreaRef.cells.map(cellRefToCell)

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

  def dummyAction(e:MouseEvent) = {
    val t = e.target
    //dom.alert(""+t)
    if((""+t).contains("Fichier")){
      currentSheet() = testSheet
    }
    if((""+t).contains("Edition")){
      val toto = currentSheet().internalGrid().map{case (k, v) => (k, v.input() + " " + v.value() + " " + v.errorTip() )}
      println(""+toto)
    }

  }



  val testSheet: Sheet = {
    val sheet= Sheet()
    for {
      col <- colDisplayRange()
      row <- rowDisplayRange()
    } yield{
      sheet.update(CellRef(col, row), s"=${col}+${row}")
    }
    sheet
  }


}