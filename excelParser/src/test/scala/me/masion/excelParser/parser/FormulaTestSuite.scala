package me.masion.excelParser.parser

import me.masion.excelParser.models.Numeric
import me.masion.excelParser.models.cellsReferences.CellRef
import me.masion.excelParser.models.functionast._
import minitest.SimpleTestSuite

import scala.util.{Failure, Success}

/**
 * Created by fred on 29/03/15.
 */
object FormulaTestSuite extends SimpleTestSuite {

  test("Numeric Input : Scientific 5.432e2") {
    val parser = new FormulaParser("5.432e2")
    //parser.InputLine.run().map(s=> println(""+s))
    assert(parser.InputLine.run() == Success(Numeric("5.432e2".toDouble)))
  }

  test("Numeric 'Formula' : =5.432e2") {
    val parser = new FormulaParser("= -5.432e-2")
//        parser.InputLine.run() match {
//          case Success(s) => println("HELLO"+s)
//          case Failure(f) => println(""+f )
//        }
    assert(parser.InputLine.run() == Success(Numeric("-5.432e-2".toDouble)))
  }

  test("Numeric 'Formula' : ' = and( A7 ) '") {
    val parser = new FormulaParser(" = and( A7 ) ")
    assert(parser.InputLine.run() == Success(And(Seq(CellRef(1,7)))))
  }

  test("Numeric 'Formula' : ' = and( A7 ; B8 ) '") {
    val parser = new FormulaParser(" = and( A7 ; B8 ) ")
    assert(parser.InputLine.run() == Success(And(Seq(CellRef(1,7), CellRef(2,8)))))
  }

  test("Numeric 'Formula' : ' = and( A7 ; -5.432e-2  ) '") {
    val parser = new FormulaParser(" = and( A7 ; -5.432e-2 ) ")
    assert(parser.InputLine.run() == Success(And(Seq(CellRef(1,7), Numeric("-5.432e-2".toDouble) ))))
  }

  test("Numeric 'Formula' : ' = or( A7 ; -5.432e-2  ) '") {
    val parser = new FormulaParser(" = or( A7 ; -5.432e-2 ) ")
    assert(parser.InputLine.run() == Success(Or(Seq(CellRef(1,7), Numeric("-5.432e-2".toDouble) ))))
  }

  test("Numeric 'Formula' : ' = Not( A7  ) '") {
    val parser = new FormulaParser(" = Not( A7 ) ")
    assert(parser.InputLine.run() == Success(Not(CellRef(1,7) )))
  }

  test("Numeric 'Formula' : ' = and( or( not( A7);  A1  ); -5.432e-2 ) '") {
    val parser = new FormulaParser(" = and( or( not( A7) ;  A1  ) ; -5.432e-2 ) ")
    assert(parser.InputLine.run() == Success( And(Seq( Or(Seq(Not(CellRef(1,7)), CellRef(1,1) )), Numeric("-5.432e-2".toDouble) )) ) )
  }

  test("Numeric 'Formula' : ' = if( or( not( A7);  A1  ); B8; -5.432e-2 ) '") {
    val parser = new FormulaParser(" = if( or( not( A7) ;  A1  ); B8 ; -5.432e-2 ) ")
    assert(parser.InputLine.run() == Success( If( Or(Seq(Not(CellRef(1,7)), CellRef(1,1) )), CellRef(2,8), Numeric("-5.432e-2".toDouble) ) ) )
  }


  test("FAILURE 'Formula' : ' = if( or( not( A7);  AA  ); B8; -5.432e-2 ) '") {
    val parser = new FormulaParser(" = if( or( not( A7) ;  AA  ); B8 ; -5.432e-2 ) ")
    assert(parser.InputLine.run().isFailure)
  }

  test("Formula before Date : ' = 5-2 '") {
    val parser = new FormulaParser(" = 5 - 2 ")
//    parser.InputLine.run() match {
//      case Success(s) => println("HELLO"+s)
//      case Failure(f) => println("HELLY"+f )
//    }
    assert(parser.InputLine.run() == Success(Substract(Numeric(5), Numeric(2))))
  }


}
