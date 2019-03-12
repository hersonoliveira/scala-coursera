package funsets

object Main extends App {
  import FunSets._
  println(contains(singletonSet(1), 1))

  val s1: Set = x => (x >= 1) && (x <= 5) // 1,2,3,4,5
  val s2: Set = x => (x >= 4) && (x < 8) // 4,5,6,7

  printSet(union(s1, s2))
  printSet(intersect(s1, s2))
  printSet(diff(s1,s2))
  printSet(filter(s1, x => (x == 2) || (x == 4)))
  println(forall(s1, x => x > 0))
  println(exists(s1, x => x == 1))
  printSet(map(s1, x => x * 2))

  val s4: Set = x => (x >= -5) && (x <= 3) // 1,2,3,4,5
  val s5: Set = x => (x >= -1) && (x < 8) // 4,5,6,7

  printSet(s4)
  printSet(s5)
}
