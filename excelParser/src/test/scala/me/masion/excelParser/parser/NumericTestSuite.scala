package me.masion.excelParser.parser

import minitest.SimpleTestSuite
import scala.util.{Failure, Success}
import me.masion.excelParser.models.Numeric

/**
 * Created by fred on 29/03/15.
 */
object NumericTestSuite extends SimpleTestSuite {

  val DECIMAL_SEP = "."

  test("Integer : 57") {
    val parser = new FormulaParser("57")
//    parser.InputLine.run() match {
//      case Success(s) => println("HELLO"+s)
//      case Failure(f) => println(""+f )
//    }
    assert(parser.InputLine.run() == Success(Numeric(57)))
  }

  test("Double 5.432") {
    val parser = new FormulaParser("5"+DECIMAL_SEP+"432")
    assert(parser.InputLine.run() == Success(Numeric(5.432)))
  }

  test("Scientific 5.432e2") {
    val parser = new FormulaParser("5"+DECIMAL_SEP+"432e2")
    assert(parser.InputLine.run() == Success(Numeric("5.432e2".toDouble)))
  }

  test("Positive Signed +5.432e2") {
    val parser = new FormulaParser(" +5"+DECIMAL_SEP+"432e+2" )
    assert(parser.InputLine.run() == Success(Numeric("5.432e2".toDouble)))
  }

  test("Negative Signed -5.432e2") {
    val parser = new FormulaParser(" -5"+DECIMAL_SEP+"432e-2 ")
    assert(parser.InputLine.run() == Success(Numeric("-5.432e-2".toDouble)))
  }
}
