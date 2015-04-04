package me.masion.front.partials

import me.masion.front.partials.table.Table

import scalatags.JsDom.all._

/**
 * Created by fred on 02/04/15.
 */
trait WrapperTable extends Table  {

    def wrapperTable = {

      div(id:="wrapper-table", cls:="wrapper-table")(
        table
      )

    }


}
