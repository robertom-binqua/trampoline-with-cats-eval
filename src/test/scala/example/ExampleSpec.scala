package example

import munit.FunSuite

class ExampleSpec extends FunSuite {

  test("Counters.count: we can sum 20000 ones with Eval") {
    val ones = 20000
    assertEquals(Example.Counters.countStackSafe(List.fill(ones)(1)), ones)
  }

  test("Example.Counter.count(List(1,2,3,4)), 10") {
    assertEquals(Example.Counters.countStackSafe((1 to 10).toList), (1 to 10).toList.sum)
  }

  test("countWithStackOverflow(List(1,2,3,4)), 10") {
    assertEquals(Example.Counters.countWithStackOverflow((1 to 10).toList), (1 to 10).toList.sum)
  }

}
