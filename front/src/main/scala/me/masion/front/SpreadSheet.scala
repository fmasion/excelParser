package me.masion.front

import scala.scalajs.js
import org.scalajs.dom
import rx._


/**
 * Created by fred on 02/04/15.
 */

object SpreadSheet extends js.JSApp with Main {

  def main(): Unit = {
    dom.document.body.appendChild(mainPage.render)
  }

}