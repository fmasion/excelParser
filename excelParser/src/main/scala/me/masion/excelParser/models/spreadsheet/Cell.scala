package me.masion.excelParser.models.spreadsheet

import me.masion.excelParser.models.{Nope, ParsingError, Primitive, Str}
import me.masion.excelParser.models.ast.ASTNode

/**
 * Created by fred on 02/04/15.
 */
case class Cell(input:String, ast:ASTNode, errorTip: ParsingError = Nope) {
  def value: Primitive = Str("")

}

object EmptyCell extends Cell("", Str("")){
  override def value=Str("")
}