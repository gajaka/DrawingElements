package commands

import org.scalatest.FunSuite

import scala.util.{Try,Success,Failure}

class DrawCommandsTest extends FunSuite {

  test("test DrawCanvas invalid height") {
    Try(DrawCanvas(20,-4)) match {
      case Success(_) => fail("Should not be here")
      case Failure(e) => succeed
    }
  }
  test("test DrawCanvas invalid width") {
    Try(DrawCanvas(-20,4)) match {
      case Success(_) => fail("Should not be here")
      case Failure(_) => succeed
    }
  }
  test("test DrawLine invalid x1") {
    Try(DrawLine(-1,2,1,2)) match {
      case Success(_) => fail("Should not be here")
      case Failure(_) => succeed
    }
  }
  test("test DrawLine invalid y1") {
    Try(DrawLine(1,-2,1,2)) match {
      case Success(_) => fail("Should not be here")
      case Failure(_) => succeed
    }
  }
  test("test DrawLine invalid x2") {
    Try(DrawLine(1,2,-1,2)) match {
      case Success(_) => fail("Should not be here")
      case Failure(_) => succeed
    }
  }
  test("test DrawLine invalid y2") {
    Try(DrawLine(1,2,1,-2)) match {
      case Success(_) => fail("Should not be here")
      case Failure(_) => succeed
    }
  }

  test("test DrawRectangle invalid x1") {
    Try(DrawRectangle(-16,1,20,3)) match {
      case Success(_) => fail("Should not be here")
      case Failure(_) => succeed
    }
  }
  test("test DrawRectangle invalid y1") {
    Try(DrawRectangle(16,-1,20,3)) match {
      case Success(_) => fail("Should not be here")
      case Failure(_) => succeed
    }
  }
  test("test DrawRectangle invalid x2") {
    Try(DrawRectangle(16,1,-20,3)) match {
      case Success(_) => fail("Should not be here")
      case Failure(_) => succeed
    }
  }
  test("test DrawRectangle invalid y2") {
    Try(DrawRectangle(16,1,20,-3)) match {
      case Success(_) => fail("Should not be here")
      case Failure(_) => succeed
    }
  }
  test("test BucketFill invalid x") {
    Try(BucketFill(-16,1,'o')) match {
      case Success(_) => fail("Should not be here")
      case Failure(_) => succeed
    }
  }
  test("test BucketFill invalid y") {
    Try(BucketFill(16,-1,'o')) match {
      case Success(_) => fail("Should not be here")
      case Failure(_) => succeed
    }
  }
}
