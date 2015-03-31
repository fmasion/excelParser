import me.masion.excelParser.models.cellsReferences.ColumnRangeRef

val str = "23A"
val radix = 16
Integer.parseInt(str, 16)

def g(c:Char) = Character.digit(c,16)
2*16*16 + 3*16+10

val toto = str.reverse.zipWithIndex.map(kv => g(kv._1) * Math.pow(radix,kv._2)).sum.toLong

val A =toto/radix
val B = A/radix
A%radix
toto%radix

radix
val z = toto - toto%radix
val z2 = z/radix
val z3 = z2/radix

z3%radix

val upperIndices: Map[Char, Long] = Map.empty ++ (('A' to 'Z').zip(1L to 26L)) // A->1 Z->26
val indicesUpper: Map[Long, Char] = upperIndices.map(kv => (kv._2, kv._1))
def dec(value:Long, acc:List[Long]=Nil):List[Long] = {
  value match {
    case v if (v > radix) => dec(v/radix, v :: acc)
    case _ => value :: acc
  }
}

dec(17).map(_ % radix).map(indicesUpper).mkString("")

ColumnRangeRef.from("A", "BB")


Math.max("27".toLong, "1".toLong)