package me.masion.front.partials

import scalatags.JsDom.all._

/**
 * Created by fred on 02/04/15.
 */
trait Footer {

  def footer = {
    div(cls:="footer")(
      "status"
    )

  }

}
