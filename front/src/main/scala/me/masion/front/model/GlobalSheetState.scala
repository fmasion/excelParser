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

  val currentSheet = Var(Sheet())
  // diplayed cell area
  val rowDisplayRange = Var(1 to 50)
  val colDisplayRange = Var(1 to 50)


  val defaultColWidth = 50
  val defaultRowHeight = 23

  val currentCell: Var[Option[CellRef]]= Var(None)
  val documentSize:Var[Point] = Var(Point(0,0))

  def currentCol = Rx { currentCell().map(_.col)}
  def currentRow = Rx { currentCell().map(_.row)}
  def cellSelected = Rx { currentCell().isDefined }

  val toto = Obs(documentSize){
    val headerHeight = jQuery(".header").height()
    val footerHeight = jQuery(".footer").height()

    var newHeight = s"${documentSize().height - headerHeight - footerHeight}px"
    jQuery(".wrapper-table").css("height", newHeight)
//    println("OBS "+ documentSize())
  }

  def setCurrentSheet(sheet:Sheet) = currentSheet() = sheet






}