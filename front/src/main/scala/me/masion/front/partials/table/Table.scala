package me.masion.front.partials.table

import me.masion.excelParser.models.{Primitive, EvaluationError}
import me.masion.front.model
import me.masion.front.model.{Point, GlobalSheetState}
import org.scalajs.dom.KeyboardEvent
import org.scalajs.dom.ext.KeyCode
import org.scalajs.dom.html.Input

import scalatags.JsDom.all._
import scalatags.rx.all._
import rx._

import org.scalajs.jquery._

/**
 * Created by fred on 03/04/15.
 */
trait Table {

  def table = div(id:="table", cls:="table")(
    container,
    inputHolder
  )

  def container = {
    //HACK PAS BO
    Obs(GlobalSheetState.currentSheet().internalGrid){
      updateContainerContant
    }
    Obs(GlobalSheetState.currentCellRef){
      updateContainerContant
    }
    div(id:= "container", cls:="container")()
  }

  def updateContainerContant = {
    jQuery("#container").empty()
    val newContent = {
      for {
        row <- GlobalSheetState.rowDisplayRange()
        col <- GlobalSheetState.colDisplayRange()
      } yield {
        val thisCellRef = GlobalSheetState.pointToCellRef(Point(col, row))
        val isSelected = GlobalSheetState.currentCellRef() == thisCellRef
        val oCell: Option[Either[EvaluationError, Primitive]] = GlobalSheetState.pointToCell(Point(col, row)).map(_.value())
        div(cls := s"cell row${row} column${col} ${if (isSelected) "selectedCell"}",
          onclick := GlobalSheetState.cellClick(col, row) _,
          ondblclick := GlobalSheetState.cellDblClick(col, row) _)(
            oCell match {
              case Some(Right(p)) => p.toString
              case Some(Left(e)) => "#VALEUR!"
              case None => ""
            }
          )
      }.render
    }
    jQuery("#container").append(newContent.toArray: _*)

  }


  def inputHolder() = {
    Obs(GlobalSheetState.currentCell){
      destroyInput
    }
    Obs(GlobalSheetState.editMode){
      if(GlobalSheetState.editMode()){
        val newInput = holderInput
        jQuery(".inputHolder").append(newInput)
      }else{
        destroyInput
      }
    }
    div(
      cls:=Rx{s"selectedCell inputHolder column${GlobalSheetState.currentCol()} row${GlobalSheetState.currentRow()}"} ,
      style:=Rx{s" ${if(!GlobalSheetState.editMode()) "display: none"}" }
    )
  }

  def destroyInput ={
    jQuery("#tableInput").remove()
  }

  def holderInput = {
    lazy val internal: Input = input(
      autofocus,
      cls := "tableInput",
      id := "tableInput",
      value := Rx {
        GlobalSheetState.currentCell().map {
          _.input()
        }.getOrElse("")
      },
      onkeyup := { (e: KeyboardEvent) =>
        e.keyCode match {
          case KeyCode.escape => destroyInput
          case KeyCode.enter => {
            GlobalSheetState.updateCurrentCell(internal.value)
            GlobalSheetState.editMode()=false
            destroyInput
          }
          case _ => ()
        }
      }
    ).render
    internal
  }

}
