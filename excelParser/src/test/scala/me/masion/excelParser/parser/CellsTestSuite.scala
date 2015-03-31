package me.masion.excelParser.parser

import me.masion.excelParser.models.cellsReferences._
import minitest.SimpleTestSuite

import scala.util.Success

/**
 * Created by fred on 29/03/15.
 */
object CellsTestSuite extends SimpleTestSuite {

  /////Alpha coordinate to Decimal
  test("should be A->1 AA ->27") {
    import me.masion.excelParser.models.cellsReferences.ColumnRef
    assert(ColumnRef.columnToPosition("A") == 1L)
    assert(ColumnRef.columnToPosition("Z")  == 26L)
    assert(ColumnRef.columnToPosition("AA") == 27L)
    assert(ColumnRef.columnToPosition("AA") == 27L)
  }

  test("should be 1->A ") {
    import me.masion.excelParser.models.cellsReferences.ColumnRef
    assert(ColumnRef.positionToColumn(1L) == "A")
    assert(ColumnRef.positionToColumn(ColumnRef.columnToPosition("ABC")) == "ABC")
  }

  /////Cell
  test("should parse a Cell A1") {
    val parser = new Tester("A1") with CellsParser
    assert(parser.Cell.run() == Success(CellRef(1,1)))
  }

  test("should parse a Cell AA120") {
    val parser = new Tester("AA120") with CellsParser
    assert(parser.Cell.run() == Success(CellRef(27,120)))
  }

  test("should parse a Cell $AA$120") {
    val parser = new Tester("$AA$120") with CellsParser
    //println(parser.Cell.run())
    assert(parser.Cell.run() == Success(CellRef(27,120)))
  }

  /////CellRange
  test("should parse a CellRange") {
    val parser = new Tester("A1:AA120") with CellsParser
    assert(parser.CellRange.run() == Success(CellRangeRef(CellRef(1,1),CellRef(27,120))))
  }

  test("should parse a CellRange with spaces") {
    val parser = new Tester("A1 :    AA120") with CellsParser
    assert(parser.CellRange.run() == Success(CellRangeRef(CellRef(1,1),CellRef(27,120))))
  }

  test("should parse an INVERTED CellRange in the right order C3:A12 -> A3:C12") {
    val parser = new Tester("C3 :A12") with CellsParser
    assert(parser.CellRange.run() == Success(CellRangeRef(CellRef(1,3),CellRef(3,12))))
  }

  /////Column Vector
  test("should parse a column Vector A:BB") {
    val parser = new Tester("A:BB") with CellsParser
    assert(parser.ColumnRange.run() == Success(ColumnRangeRef.from("A","BB")))
  }

  test("should parse a column Vector with spaces A :  BB") {
    val parser = new Tester("A :  BB") with CellsParser
    assert(parser.ColumnRange.run() == Success(ColumnRangeRef.from("A","BB")))
  }

  test("should parse a column Vector with one column A:A") {
    val parser = new Tester("A :A") with CellsParser
    assert(parser.ColumnRange.run() == Success(ColumnRangeRef.from("A","A")))
  }

  test("should parse an INVERTED column Vector B:A in the right order") {
    val parser = new Tester("B:A") with CellsParser
    assert(parser.ColumnRange.run() == Success(ColumnRangeRef(1,2)))
  }

  /////Row Vector
  test("should parse a row Vector 1:27") {
    val parser = new Tester("1:27") with CellsParser
    assert(parser.RowRange.run() == Success(RowRangeRef(1L, 27L)))
  }

  test("should parse a row Vector with spaces  1 :  27") {
    val parser = new Tester("1 :   27") with CellsParser
    assert(parser.RowRange.run() == Success(RowRangeRef(1L, 27L)))
  }

  test("should parse a row Vector one column  1:1") {
    val parser = new Tester("1 :1") with CellsParser
    assert(parser.RowRange.run() == Success(RowRangeRef(1L, 1L)))
  }

  test("should parse an INVERTED row Vector 27:1 in the right order ") {
    val parser = new Tester("27:1") with CellsParser
    assert(parser.RowRange.run() == Success(RowRangeRef(1L, 27L)))
  }

//  ////CellAreas Intersections
//  test("should parse an Intersection of a row and column Vector 'B:C 5:7'") {
//    val parser = new Tester("B :  C 5 :  7") with CellsParser
//    //parser.CellArea.run().map(s=> println(""+s))
//    assert(parser.CellArea.run() == Success(CellIntersectionRef(Seq(ColumnRangeRef.from("B", "C"), RowRangeRef(5L,7L)))))
//  }
//
//  test("Intersection of [row vector, column vector, Cell] 'B:C 5:7 C3'") {
//    val parser = new Tester("B :  C 5 :  7 C3") with CellsParser
//    assert(parser.CellArea.run() == Success(CellIntersectionRef(Seq(ColumnRangeRef.from("B", "C"), RowRangeRef(5L,7L), CellRef(3,3)))))
//  }
//
//  test("Intersection of [row vector, column vector, Cell, range] 'B:C 5:7 C3 B2 : C3'") {
//    val parser = new Tester("B :  C 5 :  7 C3  B2 : C3") with CellsParser
//    //parser.CellArea.run().map(s=> println(""+s))
//    assert(parser.CellArea.run() == Success(CellIntersectionRef(Seq(ColumnRangeRef.from("B", "C"), RowRangeRef(5L,7L), CellRef(3,3), CellRangeRef(CellRef(2,2),CellRef(3,3))     ))))
//  }
//
//  test("COMPACT Intersection of [row vector, column vector, Cell, range] 'B:C 5:7 C3 B2:C3'") {
//    val parser = new Tester("B:C 5:7 C3 B2:C3") with CellsParser
//    //parser.CellArea.run().map(s=> println(""+s))
//    assert(parser.CellArea.run() == Success(CellIntersectionRef(Seq(ColumnRangeRef.from("B", "C"), RowRangeRef(5L,7L), CellRef(3,3), CellRangeRef(CellRef(2,2),CellRef(3,3))     ))))
//  }
//
//  test("Intersection of [row vector, column vector, Cell, Union of 2 ranges] 'B:C 5:7 C3 B2 : C3 ; B 4 : C 5'") {
//    val parser = new Tester("B :  C 5 :  7 C3  B2 : C3 ; B 4 : C 5") with CellsParser
//    //parser.CellArea.run().map(s=> println(""+s))
//    assert(parser.CellArea.run() == Success(CellIntersectionRef(Seq(ColumnRangeRef.from("B", "C"), RowRangeRef(5L,7L), CellRef(3,3), CellUnionRef(Seq(CellRangeRef(CellRef(2,2),CellRef(3,3)), CellRangeRef(CellRef(2,4),CellRef(3,5)))) ))))
//  }

  //CellAreas Union
  test("should parse an Union of a row and column Vector 'B:C;5:7'") {
    val parser = new Tester("B:C;5:7") with CellsParser
    //parser.CellArea.run().map(s=> println(""+s))
    assert(parser.CellArea.run() == Success(CellUnionRef(Seq(ColumnRangeRef.from("B", "C"), RowRangeRef(5L,7L)))))
  }

  test("should parse an Union of a row and column Vector SPACED 'B :  C ;  5 :  7'") {
    val parser = new Tester("B :  C ;  5 :  7") with CellsParser
    //parser.CellArea.run().map(s=> println(""+s))
    assert(parser.CellArea.run() == Success(CellUnionRef(Seq(ColumnRangeRef.from("B", "C"), RowRangeRef(5L,7L)))))
  }

  test("should parse an Union of a row and column Vector 'B:C;5:7;B12'") {
    val parser = new Tester("B:C;5:7;B12") with CellsParser
    //parser.CellArea.run().map(s=> println(""+s))
    assert(parser.CellArea.run() == Success(CellUnionRef(Seq(ColumnRangeRef.from("B", "C"), RowRangeRef(5L,7L), CellRef(2L, 12L)))))
  }
}
