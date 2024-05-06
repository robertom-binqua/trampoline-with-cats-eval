package example

import example.Example.Eval.{FlatMap, Pure}

import scala.annotation.tailrec

object Example {

  sealed trait Eval[A] {

    @tailrec final def evaluate: A =
      this match {
        case Pure(value) => value
        case FlatMap(actualSource, actualCont) =>
          actualSource match {
            case Pure(value) =>
              // the only thing to evaluate here is the continuation
              actualCont(value).evaluate
            case FlatMap(previousSource, previousCont) =>
              previousSource.flatMap(v0 => previousCont(v0).flatMap(v1 => actualCont(v1))).evaluate
            // here, every time we call evaluate, we build 2 new FlatMaps that will produce the same result,
            // but in different way from the original. All the continuations are moved to the continuation side,
            // but of course the order is the same as you can see, and so the result.
            // Our available heap memory space get smaller but the stack will not grow thanks to the tailrec.
            // This is the crucial point:
            // previousSource -> previousCont -> actualCount is indeed our computation
          }
      }

    def flatMap[B](f: A => Eval[B]): Eval[B] = Eval.FlatMap(this, f)

  }

  object Eval {

    case class FlatMap[A, B](source: Eval[A], continuation: A => Eval[B]) extends Eval[B]

    case class Pure[A](a: A) extends Eval[A]

  }

  object Counters {

    def countWithStackOverflow(ints: List[Int]): Int =
      ints match {
        case ::(h, t) => h + countWithStackOverflow(t)
        case Nil      => 0
      }

    def countStackSafe(ints: List[Int]): Int = countWithEval(ints).evaluate

    private def countWithEval(ints: List[Int]): Eval[Int] =
      ints match {
        case ::(h, t) => Eval.Pure(h).flatMap(h => countWithEval(t).flatMap(tailSum => Eval.Pure(h + tailSum)))
        case Nil      => Eval.Pure(0)
      }
  }

}
