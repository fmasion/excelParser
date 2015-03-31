package me.masion.excelParser.models.functionast

import me.masion.excelParser.models.ast.ASTNode

/**
 * Created by fred on 29/03/15.
 */
trait LogicalFunction extends ASTNode
case class And(logicalFunctions:Seq[ASTNode]) extends LogicalFunction
case class Or(logicalFunctions:Seq[ASTNode]) extends LogicalFunction
case class Not(logicalFunction:ASTNode) extends LogicalFunction
case class If(logicalFunction:ASTNode, ifTrue:ASTNode, ifFalse:ASTNode) extends LogicalFunction

case class Equal(lhs:ASTNode, rhs:ASTNode) extends LogicalFunction
case class NotEqual(lhs:ASTNode, rhs:ASTNode) extends LogicalFunction
case class Greater(lhs:ASTNode, rhs:ASTNode) extends LogicalFunction
case class GreaterEq(lhs:ASTNode, rhs:ASTNode) extends LogicalFunction
case class Lower(lhs:ASTNode, rhs:ASTNode) extends LogicalFunction
case class LowerEq(lhs:ASTNode, rhs:ASTNode) extends LogicalFunction

object LogicalFunction{
  def from(lhs:ASTNode, op:String, rhs:ASTNode):ASTNode = op match {
    case a if(a == "<>" || a== "!=") => NotEqual(lhs, rhs)
    case ">"  => Greater(lhs, rhs)
    case "<"  => Lower(lhs, rhs)
    case "<=" => GreaterEq(lhs, rhs)
    case ">=" => LowerEq(lhs, rhs)
    case "="  => Equal(lhs, rhs)
    case _ => throw new IllegalArgumentException("BAD COMPARAISON OPERATOR")
  }

}