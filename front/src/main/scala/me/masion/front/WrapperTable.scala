package me.masion.front

import scalatags.JsDom.all._

/**
 * Created by fred on 02/04/15.
 */
trait WrapperTable {

    def wrapperTable = {
      div(id:="wrapper-table", cls:="wrapper-table")(
        div(id:="table")
      )

    }
}
