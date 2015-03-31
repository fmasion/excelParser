package me.masion.excelParser.parser

import me.masion.excelParser.models.utils.DateTime
import minitest.SimpleTestSuite
import me.masion.excelParser.models.{Dat, Numeric}
import scala.util.{Failure, Success}

/**
 * Created by fred on 29/03/15.
 */
object PrimitiveTestSuite extends SimpleTestSuite {

  test("Numeric Input : 32") {
    val parser = new FormulaParser("32")
//    parser.InputLine.run() match {
//      case Success(s) => println("HELLO"+s)
//      case Failure(f) => println(""+f )
//    }
    assert(parser.Primitive.run() == Success(Numeric("32".toDouble)))
  }

  test("Numeric Input : Scientific 5.432e2") {
    val parser = new FormulaParser(" -5.432e-2")
    assert(parser.Primitive.run() == Success(Numeric("-5.432e-2".toDouble)))
  }

  test("Date Input : 25/12/1976") {
    val parser = new FormulaParser("25/12/1976")
    assert(parser.Date.run() == Success(DateTime(1976,12,25)))
  }

  test("Date Input : 25/12/1976") {
    val parser = new FormulaParser("25/12/1976")
    assert(parser.DateInput.run() == Success(Dat(DateTime(1976,12,25))))
  }

  test("Date Input : 25/12/1976") {
    val parser = new FormulaParser("25/12/1976")
    assert(parser.Primitive.run() == Success(Dat(DateTime(1976,12,25))))
  }

  test("Date Input : 25/12/1976") {
    val parser = new FormulaParser("25/12/1976")
    assert(parser.InputLine.run() == Success(Dat(DateTime(1976,12,25))))
  }

  test("Date Input : 25/12/15") {
    val parser = new FormulaParser("25/12/15")
    assert(parser.InputLine.run() == Success(Dat(DateTime(2015,12,25))))
  }

  test("Date Input : 25/12") {
    val parser = new FormulaParser("25/12")
    val year = DateTime.now.year
    assert(parser.InputLine.run() == Success(Dat(DateTime(year,12,25))))
  }

  test("Date Input : 12/15") {
    val parser = new FormulaParser("12/15")
    assert(parser.InputLine.run() == Success(Dat(DateTime(2015,12,1))))
  }

  //Test PIVOT_DATE
  test("Date Input : 12/31") {
    val parser = new FormulaParser("12/31")
    assert(parser.InputLine.run() == Success(Dat(DateTime(1931,12,1))))
  }

  test("Time Input h:m : 20:30") {
    val parser = new FormulaParser(" 20:30 ")
    assert(parser.Time.run() == Success((20,30,0)))
  }

  test("Time Input h:m:s : 20:30:54") {
    val parser = new FormulaParser(" 20:30:54 ")
    assert(parser.Time.run() == Success((20,30,54)))
  }

  test("Date Time Input : 12/15 20:30") {
    val parser = new FormulaParser("12/15    20:30")
    assert(parser.InputLine.run() == Success( Dat(DateTime(2015,12,1, 20, 30)) ))
  }

  test("Date Input : 25/12/1976   20:30:10") {
    val parser = new FormulaParser("  25/12/1976   20:30:10  ")
    assert(parser.InputLine.run() == Success(Dat(DateTime(1976,12,25,20,30,10))))
  }

  test("Date SHORT Input : 2/1/76   0:0:0") {
    val parser = new FormulaParser(" 2/1/76   0:0:0  ")
    assert(parser.InputLine.run() == Success(Dat(DateTime(1976,1,2,0,0,0))))
  }

  test("Date SHORT Input : 02/01/76   00:00:00") {
    val parser = new FormulaParser(" 02/01/76   00:00:00  ")
    assert(parser.InputLine.run() == Success(Dat(DateTime(1976,1,2,0,0,0))))
  }


  ///With Alternative separator

  test("Date Input : 25-12-1976   20:30:10") {
    val parser = new FormulaParser("  25-12-1976   20:30:10  ")
    assert(parser.InputLine.run() == Success(Dat(DateTime(1976,12,25,20,30,10))))
  }



  ///Tests that should FAIL
  test("Date Input should FAIL : 25/12/1976   24:30:10") {
    val parser = new FormulaParser("  25/12/1976   24:30:10  ")
    assert(parser.InputLine.run().isFailure)
  }

  test("Date Input should FAIL : 25/12/1976   20:70:10") {
    val parser = new FormulaParser("  25/12/1976   24:70:10  ")
    assert(parser.InputLine.run().isFailure)
  }

  test("Date Input should FAIL : 25/12/1976   20:30:70") {
    val parser = new FormulaParser("  25/12/1976   24:30:70  ")
    assert(parser.InputLine.run().isFailure)
  }

  test("Date Input should FAIL : 25/13/1976   20:30:10") {
    val parser = new FormulaParser("  25/13/1976   24:30:10  ")
    assert(parser.InputLine.run().isFailure)
  }

  test("Date Input should FAIL : 32/12/1976   20:30:10") {
    val parser = new FormulaParser("  32/12/1976   24:30:10  ")
    assert(parser.InputLine.run().isFailure)
  }



}
