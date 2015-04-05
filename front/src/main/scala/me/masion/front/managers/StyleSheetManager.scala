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

    val range = GlobalSheetState.rowDisplayRange().toList
    val rows = GlobalSheetState.currentSheet().internalRows()
    val offset = range.head -1

    val newStyles: List[String] =  range.map{ pos =>
      getRowRule(rows.getOrElse(pos, Row.default), pos, offset)
    }
    //Add new styles
    newStyles.diff(currentRowStyles).map{ rule =>
      rowStyleSheet.insertRule(rule, rowStyleSheet.cssRules.length)
    }
    currentRowStyles = newStyles
  }

  val colStyleSheetUpdater = Obs(GlobalSheetState.colDisplayRange) {
    val range = GlobalSheetState.colDisplayRange().toList
    val cols = GlobalSheetState.currentSheet().internalColumns()
    val offset = range.head -1

    val newStyles: List[String] =  range.map{ pos =>
      getColumRule(cols.getOrElse(pos, Column.default), pos, offset)
    }
    //Add new styles
    newStyles.diff(currentColStyles).map{ rule =>
      colStyleSheet.insertRule(rule, colStyleSheet.cssRules.length)
    }
    currentColStyles = newStyles
  }


  def getRowRule(row: Row, pos:Int, offset:Int):String = {

    var rules= Map.empty[String,String]
    rules = if(row.hidden){ rules + ("display" -> "none")} else rules
    rules = rules + ("height" -> s"${row.height}px")
    rules = rules + ("top" -> s"${GlobalSheetState.currentSheet().rowTop(pos)}px")

    s"#table .row${pos - offset} { ${rules.map{case (key, value) => s"${key}: ${value};" }.mkString(" ")} ${row.styles.mkString("; ")} }"
  }


  def getColumRule(column: Column, pos:Int, offset:Int):String = {

    var rules= Map.empty[String,String]
    rules = if(column.hidden){ rules + ("display" -> "none")} else rules
    rules = rules + ("width" -> s"${column.width}px")
    rules = rules + ("left" -> s"${GlobalSheetState.currentSheet().colLeft(pos)}px")

    s"#table .column${pos - offset} { ${rules.map{case (key, value) => s"${key}: ${value};" }.mkString(" ")} ${column.styles.mkString("; ")} }"
  }



}
