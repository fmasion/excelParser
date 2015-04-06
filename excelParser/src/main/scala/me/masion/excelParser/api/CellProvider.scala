package me.masion.excelParser.api

import me.masion.excelParser.models.cellsReferences.{CellAreaRef, CellRef}

/**
 * Created by fred on 05/04/15.
 */
trait CellProvider {

  def cellRefToCell(cellRef:CellRef):Option[CellApi]

  def cellAreaToCells(cellAreaRef: CellAreaRef):Seq[CellApi]



}
