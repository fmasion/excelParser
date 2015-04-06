package me.masion.front.partials.table

import me.masion.excelParser.models.{Primitive, EvaluationError}
import me.masion.front.model.{Point, GlobalSheetState}
import org.scalajs.dom.html.Div

import scala.collection.immutable.IndexedSeq
import scalatags.JsDom.TypedTag
import scalatags.JsDom.all._
import org.scalajs.jquery._

/**
 * Created by fred on 03/04/15.
 */
trait Table {

  def table = div(id:="table", cls:="table")(
    container//,
    //overlay
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


  def inputHolder() = div(cls:="InputHolder", style:="display: none")(
    textarea(cls:="handsontableInput", style:="width: 41px; height: 23px; font-size: 14px; font-family: 'Helvetica Neue', Helvetica, Arial, sans-serif; resize: none; min-width: 41px; max-width: 1642px; overflow-y: hidden;")
  )

}
