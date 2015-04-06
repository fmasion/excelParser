package me.masion.front.model

import me.masion.excelParser.evaluator.Evaluator
import me.masion.excelParser.models.cellsReferences.{ColumnRef, CellRangeRef, CellAreaRef, CellRef}
import me.masion.excelParser.api.{CellApi, CellProvider}
import me.masion.front.managers.DomUpdater
import me.masion.front.model.spreadsheet.{Cell, Sheet}
import org.scalajs.dom.MouseEvent

import rx._

import scala.scalajs.js.annotation.{JSExportAll, JSExport}

/**
 * Created by fred on 03/04/15.
 */
case class Point(x:Double, y:Double)

object GlobalSheetState extends CellProvider with Evaluator with DomUpdater {

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

  val currentCellRef: Var[CellRef] = Var(CellRef(1,1))
  val currentCell: Rx[Option[Cell]] = Rx{ currentSheet().grid(currentCellRef()) }


  def pointToCellRef(point:Point) = CellRef((point.x + displayOffset().x).toLong, (point.y + displayOffset().y).toLong)
  override def cellRefToCell(cellRef:CellRef) = GlobalSheetState.currentSheet().grid(cellRef)
  def pointToCell(p:Point) = cellRefToCell(pointToCellRef(p))
  override def cellAreaToCells(cellAreaRef: CellAreaRef): Seq[Cell] = cellAreaRef.cells.map(cellRefToCell).collect{case Some(c) => c}

  def currentCol = Rx { currentCellRef().col}
  def currentRow = Rx { currentCellRef().row}



  def setCurrentSheet(sheet:Sheet) = currentSheet() = sheet



  def cellClick(col:Int,row:Int)( e:MouseEvent)={
    currentCellRef() = CellRef(col,row)
  }

  def cellDblClick(col:Int,row:Int)( e:MouseEvent)={
    currentCellRef() = CellRef(col,row)
    editMode() = true
  }

  def dummyAction(e:MouseEvent) = {
    val t = e.target
    //dom.alert(""+t)
    if((""+t).contains("Fichier")){
      currentSheet() = testSheet
    }
    if((""+t).contains("Edition")){
      val toto = currentSheet().internalGrid()
      println(""+toto)
    }


    domUpdaterInit

  }



  val testSheet: Sheet = {
    val sheet= Sheet()
    val testRange = 1 to 5
    for {
      col <- testRange
      row <- testRange
    } yield{
      sheet.update(CellRef(col, row), s"=${col}+${row}")
    }
    for {
      col <- testRange
    }yield{
      sheet.update(CellRef(col, 6), s"=${CellRef.from(ColumnRef.positionToColumn(col), ""+5).toPublicString}+${CellRef.from(ColumnRef.positionToColumn(col), ""+4).toPublicString}")
    }
    sheet
  }


}