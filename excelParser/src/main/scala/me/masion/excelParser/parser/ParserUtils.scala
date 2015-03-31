package me.masion.excelParser.parser

import org.parboiled2.{Parser, Rule0}

/**
 * Created by F. Masion on 25/03/15.
 * PEG global rules
 * Used by every other parsers
 */
trait ParserUtils extends Parser {

  def DECIMAL_SEP:Rule0 = rule { '.' }
  def RANGE_OPERATOR:Rule0 = rule {OptionalSpaces ~ ':' ~ OptionalSpaces }
  def UNION_OPERATOR:Rule0 = rule {OptionalSpaces ~  ';' ~ OptionalSpaces }
  def INTERSECTION_OPERATOR:Rule0 = Spaces

  //def Parens = rule { '(' ~ OptionalSpaces  ~ ANY ~ OptionalSpaces ~ ')' }
  def LParen = rule { OptionalSpaces ~ '(' ~ OptionalSpaces }
  def RParen = rule { OptionalSpaces ~ ')' ~ OptionalSpaces }

  def OptionalSpaces = rule { zeroOrMore(Space) }
  def Spaces = rule { oneOrMore(Space) }
  def Space = rule { anyOf(" \n\r\t\f") }
}
