package me.masion.excelParser.parser

import org.parboiled2.{Parser, ParserInput}

/**
 * Created by fred on 29/03/15.
 */
class Tester( val in:String) extends Parser{
  override val input:ParserInput = in.replace(" ", "")
}

