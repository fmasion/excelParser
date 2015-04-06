package me.masion.front.managers

import me.masion.front.model.GlobalSheetState
import me.masion.front.partials.table.Table
import me.masion.front.partials.{Footer, Header, WrapperTable}
import rx.core.Obs

import scalatags.JsDom.all._

/**
 * Created by fred on 02/04/15.
 */
trait DomUpdater extends Table {

  // wait for GlobalSheetState being completely updated
  def domUpdaterInit = {
    val sheetChange = Obs(GlobalSheetState.currentSheet){ updateContainerContant }

    





  }





}



