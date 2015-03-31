package me.masion.excelParser.parser

import me.masion.excelParser.models.cellsReferences._
import org.parboiled2.CharPredicate._
import org.parboiled2._

/**
 * Created by F. Masion on 25/03/15.
 * PEG rules to handle excel cells / range formats
 */
trait CellsParser extends Parser with ParserUtils {

  def CellArea:Rule1[CellAreaRef] = rule {  CellUnion | CellRange | CellVector | Cell }
  def CellArea2:Rule1[CellAreaRef] = rule { CellRange | CellVector | Cell }

  def CellUnion:Rule1[CellUnionRef] = rule { oneOrMore(CellArea2).separatedBy(UNION_OPERATOR) ~> CellUnionRef }

  // (B:C 5:7) => intersect between col B to C and row 5 to 7 => [B5;B6;B7;C5;C6;C7]
  // (B:C 5:7 C5) => intersect between col B to C and row 5 to 7 and cell C5 => [C5]
  //def CellIntersection:Rule1[CellIntersectionRef] = rule { oneOrMore(CellArea2).separatedBy(INTERSECTION_OPERATOR) ~> CellIntersectionRef}

  // B:C or 5:7
  def CellVector = rule { ColumnRange | RowRange }

  def ColumnRange:Rule1[ColumnRangeRef] = rule { capture(ColName) ~ RANGE_OPERATOR ~ capture(ColName) ~> {
    (startCol, endCol) => ColumnRangeRef.from(startCol, endCol)
  }}

  def RowRange:Rule1[RowRangeRef] = rule { capture(RowNum) ~ RANGE_OPERATOR ~ capture(RowNum) ~> {
      (startRow, endRow) => RowRangeRef.from(startRow, endRow)
  }}

  // $A$1:B2
  def CellRange:Rule1[CellRangeRef] = rule { Cell ~ RANGE_OPERATOR ~ Cell ~> ((s,e) => CellRangeRef.from(s,e)) }

  //A1 to AAA731 or $A1 or A$1
  def Cell: Rule1[CellRef]= rule { capture(optional("$") ~ ColName) ~ capture(optional("$") ~ RowNum) ~> ((r:String,c:String) => CellRef.from(r,c)) }
  //1 to n

  def RowNum = rule { Digit19 ~ oneOrMore(Digit) | Digit19}
  //A to Z then AA to ZZ then...
  def ColName = rule {oneOrMore(UpperAlpha)}


}
