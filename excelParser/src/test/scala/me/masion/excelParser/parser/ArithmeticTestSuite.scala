package me.masion.excelParser.parser

import me.masion.excelParser.models.Numeric
import me.masion.excelParser.models.cellsReferences.CellRef
import me.masion.excelParser.models.functionast.Add
import minitest.SimpleTestSuite

import scala.util.{Failure, Success}

/**
 * Created by fred on 29/03/15.
 */
object ArithmeticTestSuite extends SimpleTestSuite {

  test("Integer : 57 + 1") {
    val parser = new FormulaParser("57 + 1")
    parser.InputLine.run() match {
      case Success(s) => println("HELLO"+s)
      case Failure(f) => println(""+f )
    }
    assert(parser.InputLine.run() == Success(Add(Numeric(57), Numeric(1))))
  }

  test("Integer : A1 + B1") {
    val parser = new FormulaParser("A1 + B1")
    parser.InputLine.run() match {
      case Success(s) => println("HELLO"+s)
      case Failure(f) => println(""+f )
    }
    assert(parser.InputLine.run() == Success(Add(CellRef(1,1), CellRef(2,1))))
  }

}
