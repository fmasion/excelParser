package me.masion.excelParser.parser

import me.masion.excelParser._
import me.masion.excelParser.models.Dat
import me.masion.excelParser.models.utils.DateTime
import org.parboiled2.CharPredicate._
import org.parboiled2.{Parser, Rule0, Rule1}

import scala.util.Try

/**
 * Created by F. Masion on 25/03/15.
 * PEG rules to handle numeric formats
 * Integers => digits starting with non zero
 * Double => digits (allowing 0) with fractionnal part
 * Scientific => Double with exponant
 *
 * The result is always formated as scala Double
 */
trait DateParser extends Parser with ParserUtils  {

  def DateInput = rule { DateWithTime | Date ~> Dat }

  def DateWithTime:Rule1[Dat] = rule {
    Date ~ Time ~> ((d:DateTime, hms:(Int,Int,Int)) =>  Dat(DateTime(d.year, d.month, d.day, hour=hms._1, minute=hms._2, second=hms._3) ))
  }

  def Date:Rule1[DateTime] = rule { DayMonthYear |  DayMonth | MonthYear }

  def DayMonthYear = rule { Day ~ DateSEP ~ Month ~ DateSEP ~ Year ~> ((d:Int, m:Int,y:Int) => {
    test{Try(DateTime(y,m,d)).isSuccess} ~ push(DateTime(y,m,d))
  })}

  def MonthYear = rule { Month ~ DateSEP ~ Year ~> ((m:Int,y:Int) => {
    test{Try(DateTime(y,m,1)).isSuccess} ~ push(DateTime(y,m,1))
  })}

  def DayMonth = rule { Day ~ DateSEP ~ Month ~> ((d:Int,m:Int) => {
    val y = DateTime.now.year
    test{Try(DateTime(y,m,d)).isSuccess} ~ push(DateTime(y,m,d))
  })}

  def Day = rule { capture(Digit2) ~> ((s:String) => test(s.toInt <32) ~ push(s.toInt))}
  def Month = rule { capture(Digit2) ~> ((s:String) => test(s.toInt < 13) ~ push(s.toInt))}
  def Year = rule { Year4 | Year2 }
  def Year2 = rule { capture(Digit2) ~> {(s:String) =>
    val v = s.toInt
    if(v>PIVOT_DATE) {1900+v} else {2000+v}
  }
  }
  def Year4 = rule { capture(Digit4) ~> ((s:String) => {val v=s.toInt; test(v>=1800 && v <= 9999) ~ push(v)})}



  def Time = rule { LongTime | ShortTime }
  def ShortTime = rule { Spaces ~ hour ~ ':' ~ minute ~> ( (h,m) => (h:Int,m:Int,0) ) }
  def LongTime = rule { Spaces ~ hour ~ ':' ~ minute ~ ':' ~ second ~> ((h:Int,m:Int,s:Int) => (h,m,s))}
  def hour = rule { capture(Digit2w0) ~> ((s:String) => {
    val v = s.toInt
    test{v < 24 && v >= 0} ~ push(v)
  })}
  def second = base60
  def minute = base60
  def base60 = rule { capture(Digit2w0) ~> ((s:String) => {
    val v = s.toInt
    test{v < 60 && v >= 0} ~ push(v)
  })}


  def Digit2w0 = rule { (Digit ~ Digit | Digit) }
  def Digit2 = rule { (Digit ~ Digit | Digit19) }
  def Digit4 = rule { (Digit19 ~ Digit ~ Digit ~ Digit) }
  def DateSEP:Rule0 = rule { SlashSEP  }
  def SlashSEP = rule { DATE_SEP }
  def MinusSEP = rule {  ALT_DATE_SEP }

}
