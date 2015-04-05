package me.masion.front.partials.table

import me.masion.front.model.GlobalSheetState
import org.scalajs.dom
import org.scalajs.dom.MouseEvent
import org.scalajs.dom.html.Div

import scalatags.JsDom.TypedTag
import scalatags.JsDom.all._
import rx._
import scalatags.rx.all._

/**
 * Created by fred on 03/04/15.
 */
trait Table {

  def table = div(id:="table", cls:="table")(
    container//,
    //overlay
  )

  def container = div(cls:="container")(
    for{
      row <- GlobalSheetState.rowDisplayRange()
      col <- GlobalSheetState.colDisplayRange()
    } yield{
      div(cls:=s"cell row${row} column${col} ",
        onclick:=GlobalSheetState.cellClick(col,row) _ ,
        ondblclick:=GlobalSheetState.cellDblClick(col,row) _ )(
        ""+col + "," +row
      )
    }
  )

  //def overlay = div(cls:="overlay", onclick:=GlobalSheetState.overlayClick _ )

//  def inputHolder = div(cls:="handsontableInputHolder", style:="display: none")(
//    textarea(cls:="handsontableInput", style:="width: 41px; height: 23px; font-size: 14px; font-family: 'Helvetica Neue', Helvetica, Arial, sans-serif; resize: none; min-width: 41px; max-width: 1642px; overflow-y: hidden;")
//  )

//  def colResizerGuide = div(cls:="manualColumnResizer", style:="top: 120px; left: 228px;")
//
//  def colResizer = div(cls:="manualColumnResizerGuide", style:="top: 120px; left: 128px; height: 640px;" )
//


}
