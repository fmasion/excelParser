package me.masion.excelParser.parser

import org.parboiled2.CharPredicate._
import me.masion.excelParser.models.Numeric
import org.parboiled2.{Rule1, Parser}

/**
 * Created by F. Masion on 25/03/15.
 * PEG rules to handle numeric formats
 * Integers => digits starting with non zero
 * Double => digits (allowing 0) with fractionnal part
 * Scientific => Double with exponant
 *
 * The result is always formated as scala Double
 */
trait NumericParser extends Parser with ParserUtils  {

  def Number:Rule1[Numeric] = rule { OptionalSpaces ~ Double | Entier ~ OptionalSpaces }
  def Double = rule { capture(optional(Signe) ~ Integer ~ FracExp) ~> (n => Numeric(n.toDouble))}
  def Entier = rule { capture(optional(Signe) ~ Integer) ~> (n=> Numeric(n.toDouble)) }
  def FracExp =  rule{ Fract ~ Exposant | Fract | Exposant}
  def Exposant = rule { ignoreCase('e') ~ optional(Signe) ~ Digits }
  def Signe = rule { anyOf("+-") }
  def Fract  = rule { DECIMAL_SEP ~ Digits}
  def Integer = rule { Digit19 ~ Digits | Digit}
  def Digits = rule {oneOrMore(Digit)}

}
