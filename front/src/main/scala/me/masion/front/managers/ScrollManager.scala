package me.masion.front.managers

import me.masion.front.model.{GlobalSheetState, Point}
import org.scalajs.dom
import org.scalajs.jquery._
import rx._

/**
  * Created by fred on 04/04/15.
  */
trait ScrollManager {

 val scroll = jQuery(dom.window).ready{ () => {
   val elem = jQuery("#wrapper-table")
   elem.scroll{ () =>
    val topScroll = elem.scrollTop()
    val leftScroll = elem.scrollLeft()

    GlobalSheetState.scrollPosition() = Point(topScroll, leftScroll)

   }
 }}


}
