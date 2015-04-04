package me.masion.front.partials.table

import org.scalajs.dom
import org.scalajs.dom.html.Div

import scalatags.JsDom.TypedTag
import scalatags.JsDom.all._
import rx._
import scalatags.rx.all._

/**
 * Created by fred on 03/04/15.
 */
trait Table {

  def table = div(id:="table", cls:="handsontable")(
    htContainer
  )

  def htContainer = div(cls:="htContainer")(
    div( cls:="wtHolder ht_master", style:="position: relative; width: 3000px; height: 2000px;")(
      "coucou"
    )
  )
//
//  def inputHolder = div(cls:="handsontableInputHolder", style:="display: none")(
//    textarea(cls:="handsontableInput", style:="width: 41px; height: 23px; font-size: 14px; font-family: 'Helvetica Neue', Helvetica, Arial, sans-serif; resize: none; min-width: 41px; max-width: 1642px; overflow-y: hidden;")
//  )
//
//  def colResizerGuide = div(cls:="manualColumnResizer", style:="top: 120px; left: 228px;")
//
//  def colResizer = div(cls:="manualColumnResizerGuide", style:="top: 120px; left: 128px; height: 640px;" )
//


}
