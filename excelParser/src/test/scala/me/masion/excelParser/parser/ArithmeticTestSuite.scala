package me.masion.excelParser.parser

import me.masion.excelParser.models.Numeric
import minitest.SimpleTestSuite

import scala.util.Success

/**
 * Created by fred on 29/03/15.
 */
object ArithmeticTestSuite extends SimpleTestSuite {

  val DECIMAL_SEP = "."

  test("Integer : 57") {
    val parser = new Tester("57") with NumericParser
    //parser.Number.run().map(s=> println(""+s))
    assert(parser.Number.run() == Success(Numeric(57)))
  }

}
