package me.masion.front.partials.table

import me.masion.excelParser.models.{Primitive, EvaluationError}
import me.masion.front.model.{Point, GlobalSheetState}

import scalatags.JsDom.all._
import scalatags.rx.all._
import rx._

import org.scalajs.jquery._

/**
 * Created by fred on 03/04/15.
 */
trait Table {

  def table = div(id:="table", cls:="table")(
    container //,
    //inputHolder
  )

  def container = div(id:= "container", cls:="container")()

  def updateContainerContant = {
    jQuery("#container").empty()
    val newContent = for{
      row <- GlobalSheetState.rowDisplayRange()
      col <- GlobalSheetState.colDisplayRange()
    } yield{
      val thisCellRef = GlobalSheetState.pointToCellRef(Point(col, row))
      val isSelected = GlobalSheetState.currentCellRef() == thisCellRef
      val oCell: Option[Either[EvaluationError, Primitive]] = GlobalSheetState.pointToCell(Point(col, row)).map(_.value())
      div(cls:=s"cell row${row} column${col} ${if(isSelected) "selectedCell"}",
        onclick:=GlobalSheetState.cellClick(col,row) _ ,
        ondblclick:=GlobalSheetState.cellDblClick(col,row) _ )(
          oCell match{
            case Some(Right(p)) => p.toString
            case Some(Left(e))  => "#VALEUR!"
            case None           => ""
          }
        )
    }.render

    jQuery("#container").append(newContent.toArray:_*)

  }


  def inputHolder() = div(
    cls:=Rx{s"InputHolder column${GlobalSheetState.currentCol()} row${GlobalSheetState.currentRow()}"} ,
    style:=Rx{s" ${if(!GlobalSheetState.editMode()) "display: none"}" }
  )(
      textarea(
        cls:="handsontableInput",
        value:= Rx{GlobalSheetState.currentCell().map{_.input()}.getOrElse("")}
  ))

}
