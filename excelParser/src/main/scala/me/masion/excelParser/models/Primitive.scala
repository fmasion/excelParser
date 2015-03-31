package me.masion.excelParser.models

import me.masion.excelParser.models.ast.ASTNode
import me.masion.excelParser.models.functionast.{ArithmeticFunction, LogicalFunction}
import me.masion.excelParser.models.utils.DateTime

/**
 * Created by fred on 27/03/15.
 */
sealed trait Primitive extends ASTNode
case class Numeric(value: Double) extends Primitive with ArithmeticFunction
case class Bool(value: Boolean) extends Primitive with LogicalFunction with ArithmeticFunction
case class Str(value: String) extends Primitive with ArithmeticFunction
case class Dat(value: DateTime) extends Primitive with ArithmeticFunction


object Primitive{
  implicit def NumericToBool(num:Numeric):Bool = num match{
    case Numeric(0) => Bool(false)
    case _ => Bool(true)
  }

  implicit def BoolToNumeric(num:Bool):Numeric = num match{
    case Bool(true) => Numeric(1)
    case _ => Numeric(0)
  }

  implicit def DatToNumeric(dat:Dat):Numeric = Numeric(dat.value.toDouble)


  implicit def DatToBool(dat:Dat):Numeric = dat match{
    case Dat(value) if(value.toDouble == 0) => Bool(false)
    case _ => Bool(true)
  }


}