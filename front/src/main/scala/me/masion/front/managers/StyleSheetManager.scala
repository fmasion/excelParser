package me.masion.front.managers

import me.masion.front.model.GlobalSheetState
import me.masion.front.model.spreadsheet.{Column, Row}
import org.scalajs.dom
import org.scalajs.dom._
import org.scalajs.dom.raw.{CSSRuleList, CSSStyleSheet}
import rx._

import scala.scalajs.js

/**
 * Created by fred on 04/04/15.
 */
trait StyleSheetManager {

  var currentColStyles: List[String] = List.empty //position/rule
  var currentRowStyles: List[String] = List.empty //position/rule
  val colStyleSheet = getOrCreateStyleSheet("tableColumn")
  val rowStyleSheet = getOrCreateStyleSheet("tableRow")

  def getOrCreateStyleSheet(id:String) = {
    val stylesheet = Option(document.getElementById(id).asInstanceOf[js.Dynamic]).map(_.sheet).getOrElse{
      val newStyle = dom.document.createElement("style").asInstanceOf[CSSStyleSheet]
      newStyle.id = id
      dom.document.head.appendChild(newStyle.asInstanceOf[Node])
      newStyle.asInstanceOf[js.Dynamic].sheet
    }
    stylesheet.asInstanceOf[CSSStyleSheet]
  }

  val rowStyleSheetUpdater = Obs(GlobalSheetState.rowDisplayRange) {
    val newStyles: List[String] =  GlobalSheetState.rowDisplayRange().toList.map{ i =>
      getRowRule(GlobalSheetState.currentSheet().internalRows().getOrElse(i, Row.default), i)
    }
    //Add new styles
    newStyles.diff(currentRowStyles).map{ rule =>
      rowStyleSheet.insertRule(rule, rowStyleSheet.cssRules.length)
    }
    currentRowStyles = newStyles
  }

  val colStyleSheetUpdater = Obs(GlobalSheetState.colDisplayRange) {
    val newStyles: List[String] =  GlobalSheetState.colDisplayRange().toList.map{ i =>
      getColumRule(GlobalSheetState.currentSheet().internalColumns().getOrElse(i, Column.default), i)
    }
    //Add new styles
    newStyles.diff(currentColStyles).map{ rule =>
      colStyleSheet.insertRule(rule, colStyleSheet.cssRules.length)
    }
    currentColStyles = newStyles
  }


  def getRowRule(row: Row, i:Int):String = {
    s".table .row${i} { }"
  }


  def getColumRule(column: Column, i:Int):String = {
    s".table .column${i} { }"
  }



}
