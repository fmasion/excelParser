package me.masion.excelParser.parser

import minitest.SimpleTestSuite
import scala.util.Success
import me.masion.excelParser.models.Numeric

/**
 * Created by fred on 29/03/15.
 */
object NumericTestSuite extends SimpleTestSuite {

  val DECIMAL_SEP = "."

  test("Integer : 57") {
    val parser = new Tester("57") with NumericParser
    //parser.Number.run().map(s=> println(""+s))
    assert(parser.Number.run() == Success(Numeric(57)))
  }

  test("Double 5.432") {
    val parser = new Tester("5"+DECIMAL_SEP+"432") with NumericParser
    //parser.Numeric.run().map(s=> println(""+s))
    assert(parser.Number.run() == Success(Numeric(5.432)))
  }

  test("Scientific 5.432e2") {
    val parser = new Tester("5"+DECIMAL_SEP+"432e2") with NumericParser
    //parser.Number.run().map(s=> println(""+s))
    assert(parser.Number.run() == Success(Numeric("5.432e2".toDouble)))
  }

  test("Positive Signed +5.432e2") {
    val parser = new Tester(" +5"+DECIMAL_SEP+"432e+2" ) with NumericParser
    //parser.Number.run().map(s=> println(""+s))
    assert(parser.Number.run() == Success(Numeric("5.432e2".toDouble)))
  }

  test("Negative Signed -5.432e2") {
    val parser = new Tester(" -5"+DECIMAL_SEP+"432e-2 ") with NumericParser
    //parser.Number.run().map(s=> println(""+s))
    assert(parser.Number.run() == Success(Numeric("-5.432e-2".toDouble)))
  }
}
