package me.masion.front.model.spreadsheet

import me.masion.front.model.GlobalSheetState
import rx._

/**
 * Created by fred on 02/04/15.
 */
case class Column(width: Int, format:String, styles:Set[String]=Set.empty, hidden:Boolean=false) {

}


object Column{
  val default = Column(GlobalSheetState.defaultColWidth, "")
}
