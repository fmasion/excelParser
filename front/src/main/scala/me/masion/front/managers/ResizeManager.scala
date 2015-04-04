package me.masion.front.managers

import me.masion.front.model.{GlobalSheetState, Point}
import org.scalajs.dom
import org.scalajs.jquery._

/**
  * Created by fred on 04/04/15.
  */
trait ResizeManager {

  val doc = jQuery(dom.document)

  jQuery(dom.window).ready{ () => {
    GlobalSheetState.documentSize() = Point(doc.height(), doc.width())
  }}

  jQuery(dom.window).resize{ () => {
    GlobalSheetState.documentSize() = Point(doc.height(), doc.width())
  }}

 }
