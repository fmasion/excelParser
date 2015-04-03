package me.masion.front.model.spreadsheet

import me.masion.excelParser.models.ast.ASTNode
import me.masion.excelParser.models.{Nope, ParsingError, Primitive, Str}

/**
 * Created by fred on 02/04/15.
 */
case class Cell(input:String, ast:ASTNode, errorTip: ParsingError = Nope, rowSpan:Int=1, colSpan:Int=1) {
  def value: Primitive = Str("")

}

object EmptyCell extends Cell("", Str("")){
  override def value=Str("")
}