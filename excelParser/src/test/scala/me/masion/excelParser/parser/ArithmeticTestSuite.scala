package me.masion.excelParser.parser

import me.masion.excelParser.models.{Dat, Numeric}
import me.masion.excelParser.models.cellsReferences.CellRef
import me.masion.excelParser.models.functionast._
import me.masion.excelParser.models.utils.DateTime
import minitest.SimpleTestSuite

import scala.util.{Failure, Success}

/**
 * Created by fred on 29/03/15.
 */
object ArithmeticTestSuite extends SimpleTestSuite {

  test("Integer : =57 + 1") {
    val parser = new FormulaParser("=57+1")
//    parser.InputLine.run() match {
//      case Success(s) => println("HELLO"+s)
//      case Failure(f) => println(""+f )
//    }
    assert(parser.InputLine.run() == Success(Add(Numeric(57), Numeric(1))))
  }

  test("cell : A1 + B1") {
    val parser = new FormulaParser(" = A1 + B1")
    assert(parser.InputLine.run() == Success(Add(CellRef(1,1), CellRef(2,1))))
  }

  test("cell : 57 + B1") {
    val parser = new FormulaParser(" = 57 + B1")
    assert(parser.InputLine.run() == Success(Add(Numeric(57), CellRef(2,1))))
  }

  test("Integer : = A1 + B1") {
    val parser = new FormulaParser("= A1 + B1")
    assert(parser.InputLine.run() == Success(Add(CellRef(1,1), CellRef(2,1))))
  }

  test("Integer : = if( or( not( A7) ;  A1  ); B8 ; -5.432e-2 ) * 2 + B1") {
    val parser = new FormulaParser("= if( or( not( A7) ;  A1  ); B8 ; -5.432e-2 ) * 2 + 20/4/2015 ")
    assert(parser.InputLine.run() == Success(Add(Multiply(If( Or(Seq(Not(CellRef(1,7)), CellRef(1,1) )), CellRef(2,8), Numeric("-5.432e-2".toDouble) ), Numeric(2)) , Dat(DateTime(2015,4,20)))))
  }

  test("cell : A1 - B1") {
    val parser = new FormulaParser(" = A1 - B1")
    assert(parser.InputLine.run() == Success(Substract(CellRef(1,1), CellRef(2,1))))
  }

  test("cell : A1 / B1") {
    val parser = new FormulaParser(" = A1 / B1")
    assert(parser.InputLine.run() == Success(Divide(CellRef(1,1), CellRef(2,1))))
  }

  test("cell : A1 ^ B1") {
    val parser = new FormulaParser(" = A1 ^ B1")
    assert(parser.InputLine.run() == Success(Exponant(CellRef(1,1), CellRef(2,1))))
  }


}
