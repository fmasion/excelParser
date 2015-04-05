package me.masion.front.partials

import me.masion.front.model.GlobalSheetState

import scalatags.JsDom.all._
import scalatags.rx.all._
import rx._

/**
 * Created by fred on 02/04/15.
 */
trait Footer {

  def footer = {
    div(cls:="footer")(
       Rx{s"status: ${if(GlobalSheetState.editMode()){s"EDIT ${GlobalSheetState.currentCellDiv()}"} else{"BROWSE"}  }"}
    )

  }

}
