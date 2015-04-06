package me.masion.front

import me.masion.front.managers.{ScrollManager, StyleSheetManager, ResizeManager, DisplayManager}
import me.masion.front.model.spreadsheet.Sheet
import me.masion.front.model.{Point, GlobalSheetState}
import org.scalajs.dom._
import org.scalajs.dom.raw.CSSStyleSheet

import scala.scalajs.js
import org.scalajs.dom
import org.scalajs.jquery._
import rx._


/**
 * Created by fred on 02/04/15.
 */
object SpreadSheet extends js.JSApp with DisplayManager with ResizeManager with StyleSheetManager with ScrollManager {

  def main(): Unit = {
    initModel
    dom.document.body.appendChild(mainPage.render)
    updateContainerContant
  }

  def initModel() = {
    GlobalSheetState.currentSheet() = Sheet()
  }



}