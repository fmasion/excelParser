package me.masion.front.managers

import me.masion.front.model.{GlobalSheetState, Point}
import org.scalajs.dom
import org.scalajs.jquery._
import rx._

/**
  * Created by fred on 04/04/15.
  */
trait ScrollManager {


 jQuery(dom.window).ready{ () => {
   val elem = jQuery("#wrapper-table")
  println("" + elem)
   elem.scroll{ () =>
    val topScroll = elem.scrollTop()
    val leftScroll = elem.scrollLeft()

    GlobalSheetState.scrollPosition() = Point(topScroll, leftScroll)

   }
 }}





//
//  jQuery(dom.window).resize{ () => {
//    documentSize() = Point(doc.height(), doc.width())
//  }}
//
//  val Resizer = Obs(documentSize){
//    val headerHeight = jQuery(".header").height()
//    val footerHeight = jQuery(".footer").height()
//
//    var newHeight = s"${documentSize().height - headerHeight - footerHeight}px"
//    jQuery(".wrapper-table").css("height", newHeight)
//  }

 }
