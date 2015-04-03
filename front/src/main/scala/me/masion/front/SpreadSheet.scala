package me.masion.front

import me.masion.front.model.{Point, GlobalSheetState}

import scala.scalajs.js
import org.scalajs.dom
import org.scalajs.jquery._
import rx._


/**
 * Created by fred on 02/04/15.
 */
object SpreadSheet extends js.JSApp with Main {

  def main(): Unit = {
    dom.document.body.appendChild(mainPage.render)

    val doc = jQuery(dom.document)

    jQuery(dom.window).ready{ () => {
      GlobalSheetState.documentSize() = Point(doc.height(), doc.width())
    }}

    jQuery(dom.window).resize{ () => {
      GlobalSheetState.documentSize() = Point(doc.height(), doc.width())
    }}

  }

}