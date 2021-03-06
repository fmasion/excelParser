package me.masion.excelParser.models.cellsReferences

import me.masion.excelParser.models.ast.ASTNode

/**
 * Created by fred on 27/03/15.
 */


sealed trait CellAreaRef{
  def cells:Seq[CellRef]
}
case class CellRef(col:Long, row:Long, dollarX:Boolean=false, dollarY:Boolean=false) extends CellAreaRef with ASTNode {
  override def cells: Seq[CellRef] = Seq(this)
  def toPublicString = (s"${withDollar(dollarX)}${ColumnRef.positionToColumn(col)}${withDollar(dollarY)}${row}")
  private def withDollar(b:Boolean) = if(b) "$" else ""
}
case class CellRangeRef(startRef: CellRef, endRef: CellRef) extends CellAreaRef {
  override def cells: Seq[CellRef] = for{
    col <- startRef.col to endRef.col
    row <- startRef.row to endRef.col
  }yield{
      CellRef(col,row)
    }
}
case class CellIntersectionRef(cellAreas:Seq[CellAreaRef]) extends CellAreaRef {
  override def cells: Seq[CellRef] = {
    //TODO
    Seq.empty
  }
}
case class CellUnionRef(cellAreas:Seq[CellAreaRef]) extends CellAreaRef {
  override def cells: Seq[CellRef] = {
    cellAreas.foldLeft(Seq.empty[CellRef])(_ ++ _.cells ).distinct
  }
}

sealed trait CellVectorRef
case class ColumnRangeRef(startCol: Long, endCol: Long) extends CellAreaRef with CellVectorRef {
  override def cells: Seq[CellRef] = {
    //TODO
    Seq.empty
  }
}
case class RowRangeRef(startRow: Long, endRow: Long) extends CellAreaRef with CellVectorRef {
  override def cells: Seq[CellRef] = {
    //TODO
    Seq.empty
  }
}



object CellRef{
  def from(col:String, row:String) = {
    val dollarX = col.contains("$")
    val dollarY = row.contains("$")
    val ccol = col.replace("$", "")
    val crow = row.replace("$", "")
    CellRef(ColumnRef.columnToPosition(ccol),crow.toLong, dollarX, dollarY)
  }
}

object CellRangeRef{
  /**
   * @param startCellRef
   * @param endCellRef
   * @return a well ordered CellRangeRef
   */
  def from(startCellRef: CellRef, endCellRef: CellRef) = {
    val (sr,er) = (startCellRef.col, endCellRef.col)
    val (sc,ec) = (startCellRef.row, endCellRef.row)
    CellRangeRef(CellRef(Math.min(sr,er),Math.min(sc,ec)), CellRef(Math.max(sr,er),Math.max(sc,ec)))
  }
}

object ColumnRangeRef{
  /**
   * @param startCol
   * @param endCol
   * @return a well ordered ColumnRangeRef
   */
  def from(startCol: String, endCol: String) = {
    val (s,e) = (ColumnRef.columnToPosition(startCol), ColumnRef.columnToPosition(endCol))
    ColumnRangeRef(Math.min(s,e),Math.max(s,e))
  }
}

object RowRangeRef{
  /**
   * @param startRow
   * @param endRow
   * @return a well ordered RowRangeRef
   */
  def from(startRow: String, endRow: String) = {
    val (s,e) = (startRow.toLong, endRow.toLong)
    RowRangeRef(Math.min(s,e),Math.max(s,e))
  }
}

object ColumnRef{
  private val upperIndices: Map[Char, Long] = Map.empty ++ (('A' to 'Z').zip(1L to 26L)) // A->1 Z->26
  private val indicesUpper: Map[Long, Char] = upperIndices.map(kv => (kv._2, kv._1))
  private val radix = 26L

  /**
   * @param coord
   * @return the Long value of the Alpha coordinate A -> 1, AA -> 27...
   */
  def columnToPosition(coord:String):Long = {
    coord.reverse.zipWithIndex.map((kv: (Char, Int)) => upperIndices(kv._1) * Math.pow(radix,kv._2)).sum.toLong
  }

  /**
   * @param coord
   * @return the Alpha coordinate of a Long 1 -> A, 27 -> AA...
   */
  def positionToColumn(coord:Long):String = {
    def dec(value:Long, acc:List[Long]=Nil):List[Long] = {
      value match {
        case v if (v > radix) => dec(v/radix, v :: acc)
        case _ => value :: acc
      }
    }
    dec(coord).map(_ % radix).map(indicesUpper).mkString("")
  }


}