package me.masion.front

import scalatags.JsDom.all._

/**
 * Created by fred on 02/04/15.
 */
trait WrapperTable {

    def wrapperTable = {
      div(cls:="wrapper-table")(
        div(id:="table")
      )

    }
}
