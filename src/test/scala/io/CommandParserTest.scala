package io

import commands.{BucketFill, DrawCanvas, DrawLine, DrawRectangle, Quit,InvalidCommand}
import org.scalatest.FunSuite

class CommandParserTest extends FunSuite {
test("input string: C 20 4") {
  val CommandResolver(c) =  Parser("C 20 4")
  c match {
    case canvas:DrawCanvas =>
      assert(canvas.h == 4)
      assert(canvas.w == 20)
    case _ => fail("Should not be here")
  }
}

  test("input string: L 1 2 6 2") {
   val CommandResolver(c) =  Parser("L 1 2 6 2")
    c match {
      case line:DrawLine =>
        assert(line.x1 == 1)
        assert(line.y1 == 2)
        assert(line.x2 == 6)
        assert(line.y2 == 2)
      case _ => fail("Should not be here")
    }
  }
  test("input string: R 16 1 20 3") {
    val CommandResolver(c) =  Parser("R 16 1 20 3")
    c match {
      case rectangle:DrawRectangle =>
        assert(rectangle.x1 == 16)
        assert(rectangle.y1 == 1)
        assert(rectangle.x2 == 20)
        assert(rectangle.y2 == 3)
      case _ => fail("Should not be here")
    }
  }

  test("input string: B 10 3 o") {
    val CommandResolver(c) =  Parser("B 10 3 o")
    c match {
      case bucketFill:BucketFill =>
        assert(bucketFill.x == 10)
        assert(bucketFill.y == 3)
        assert(bucketFill.c == 'o')
      case _ => fail("Should not be here")
    }
  }

  test("input string: q") {
    val CommandResolver(c) =  Parser("q")
    c match {
      case _:Quit => succeed
      case _ => fail("Should not be here")
    }
  }

  test("Malicious input string: C 12") {
    val CommandResolver(c) = Parser("C 12")
    c match {
      case _:InvalidCommand => succeed
      case _ => fail("Should not be here")
    }

  }
}
