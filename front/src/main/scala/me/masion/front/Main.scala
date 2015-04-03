package me.masion.front

import org.scalajs.dom

import scalatags.JsDom.all._
import rx._
import scalatags.rx.all._
/**
 * Created by fred on 02/04/15.
 */
trait Main extends Header with Footer with WrapperTable {

  def mainPage = {
    div(cls :="wrapper")(
      header,
      wrapperTable,
      footer

    )

  }

}



