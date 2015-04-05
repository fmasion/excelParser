package me.masion.front.managers

import me.masion.front.model.{GlobalSheetState, Point}
import org.scalajs.dom
import org.scalajs.jquery._
import rx._

/**
  * Created by fred on 04/04/15.
  */
trait ResizeManager {

  val doc = jQuery(dom.document)

  jQuery(dom.window).ready{ () => {
    GlobalSheetState.documentSize() = Point(doc.width(),doc.height() )
  }}

  jQuery(dom.window).resize{ () => {
    GlobalSheetState.documentSize() = Point(doc.width(),doc.height())
  }}

  val Resizer = Obs(GlobalSheetState.documentSize){
    val headerElem = jQuery(".header")
    val footerElem = jQuery(".footer")
    val newHeight = s"${GlobalSheetState.documentSize().y - headerElem.height() - footerElem.height()}px"
    jQuery(".wrapper-table").css("height", newHeight)
  }

 }
