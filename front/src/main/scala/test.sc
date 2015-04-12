import scala.annotation.tailrec
import scala.collection.Traversable
@tailrec
def sumUntil(acc:(Int,Int))(list: Iterator[Int], endCondition: Int => Boolean):Int = {
  if (list.hasNext) {
    val next = list.next()
    if (!endCondition(next + acc._2)) {
      sumUntil(acc._1 + 1, next + acc._2)(list, endCondition)
    } else {
      acc._1
    }
  } else {
    acc._1
  }
}



val r = Iterator.iterate(1)(e => e+1)

sumUntil(1,0)(r, _>=10)
