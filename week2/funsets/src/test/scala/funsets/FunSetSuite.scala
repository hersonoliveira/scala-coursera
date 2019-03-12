package funsets

import org.scalatest.FunSuite


import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner

/**
 * This class is a test suite for the methods in object FunSets. To run
 * the test suite, you can either:
 *  - run the "test" command in the SBT console
 *  - right-click the file in eclipse and chose "Run As" - "JUnit Test"
 */
@RunWith(classOf[JUnitRunner])
class FunSetSuite extends FunSuite {

  /**
   * Link to the scaladoc - very clear and detailed tutorial of FunSuite
   *
   * http://doc.scalatest.org/1.9.1/index.html#org.scalatest.FunSuite
   *
   * Operators
   *  - test
   *  - ignore
   *  - pending
   */

  /**
   * Tests are written using the "test" operator and the "assert" method.
   */
  // test("string take") {
  //   val message = "hello, world"
  //   assert(message.take(5) == "hello")
  // }

  /**
   * For ScalaTest tests, there exists a special equality operator "===" that
   * can be used inside "assert". If the assertion fails, the two values will
   * be printed in the error message. Otherwise, when using "==", the test
   * error message will only say "assertion failed", without showing the values.
   *
   * Try it out! Change the values so that the assertion fails, and look at the
   * error message.
   */
   test("adding ints") {
     assert(1 + 2 === 3)
   }


  import FunSets._

  test("contains is implemented") {
    assert(contains(x => true, 100))
  }

  test("contains in range") {
    assert(contains(x => (x > 0) && (x < 100), 50))
  }


  /**
   * When writing tests, one would often like to re-use certain values for multiple
   * tests. For instance, we would like to create an Int-set and have multiple test
   * about it.
   *
   * Instead of copy-pasting the code for creating the set into every test, we can
   * store it in the test class using a val:
   *
   *   val s1 = singletonSet(1)
   *
   * However, what happens if the method "singletonSet" has a bug and crashes? Then
   * the test methods are not even executed, because creating an instance of the
   * test class fails!
   *
   * Therefore, we put the shared values into a separate trait (traits are like
   * abstract classes), and create an instance inside each test method.
   *
   */

  trait TestSets {
    val s1 = singletonSet(1)
    val s2 = singletonSet(2)
    val s3 = singletonSet(3)

    val s4: Set = x => (x >= -5) && (x <= 3) // {-5,-4,-3,-2,-1,0,1,2,3}
    val s5: Set = x => (x >= -1) && (x < 8) // {-1,0,1,2,3,4,5,6,7}

    val s6 = singletonSet(0)
  }

  /**
   * This test is currently disabled (by using "ignore") because the method
   * "singletonSet" is not yet implemented and the test would fail.
   *
   * Once you finish your implementation of "singletonSet", exchange the
   * function "ignore" by "test".
   */
  test("singletonSet(1) contains 1") {
    new TestSets {
      assert(contains(s1, 1), "Singleton")
    }
  }

  test("union contains all elements of each set") {
    new TestSets {
      val s = union(s1, s2)
      assert(contains(s, 1), "Union 1")
      assert(contains(s, 2), "Union 2")
      assert(!contains(s, 3), "Union 3")
    }
  }

  test("union doesn't contain elements") {
    new TestSets {
      val s = union(s4,s5)
      assert(!contains(s, -10), "not contains -10")
      assert(!contains(s, 8), "not contains 8")
    }
  }

  test("intersect") {
    new TestSets {
      val s = intersect(s4,s5)
      assert(contains(s, -1), "contains -1")
      assert(contains(s, 0), "contains 0")
      assert(!contains(s, -2), "not contains -2")
      assert(!contains(s, 4), "not contains 4")
    }
  }

  test("diff") {
    new TestSets {
      val s = diff(s4,s5)
      assert(contains(s, -5), "contains -5")
      assert(contains(s, -2), "contains -2")
      assert(!contains(s, -1), "not contains -1")
      assert(!contains(s, 7), "not contains 7")
    }
  }

  test("filter") {
    new TestSets {
      val s = filter(s4, (x: Int) => (x == 2) || (x == 333))
      assert(contains(s, 2), "contains 2")
      assert(!contains(s, 333), "not contains 333")
    }
  }

  test("forall") {
    new TestSets {
      assert(forall(s4, (x: Int) => x > -10), "all set elements bigger than -10")
      assert(!forall(s4, (x: Int) => x > 3), "only some set elements bigger than 3")
    }
  }

  test("exists") {
    new TestSets {
      assert(exists(s4, (x: Int) => x == 1), "predicate is true to one element")
      assert(!exists(s4, (x: Int) => x < -10), "predicate is false to all elements")
    }
  }

  test("map") {
    new TestSets {
      val s = map(s4, (x: Int) => x * 2)
      assert(contains(s, 6), "contains 6")
      assert(contains(s, -10), "contains -10")
      assert(contains(s, 0), "contains 0")
      assert(!contains(s, -1), "not contains -1")
    }
  }
}
