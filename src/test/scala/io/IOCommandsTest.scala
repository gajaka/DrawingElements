package io

import org.scalatest.FunSuite

import io.IOCommands._

class IOCommandsTest extends FunSuite {

  test("testB") {
    val bf ="B 10 3 o"
    B.findFirstIn (bf) match {
      case Some(s) => assert(s == bf)
      case None => fail("Should not be here")
    }
  }

  test("test malicious B") {
    val bf ="B 10 3"
    B.findFirstIn (bf) match {
      case Some(_) => fail("Should not be here")
      case None => succeed
    }
  }

  test("testC") {
    val c = "C 20 4"
    C.findFirstIn(c) match {
      case Some(s) => assert(s == c)
      case None => fail()
    }
  }

  test("testL") {
    val l ="l 1 2 6 2"
    val str =L.findAllIn(l).foldRight("")( (acc,char) => acc + char)
    assert(str==l)
  }

  test("testQ") {
    val q ="Q"
     Q.findFirstIn(q) match {
       case Some(s) => assert (s == q)
       case None => fail()
     }
  }

  test("testR") {
    val rec = "R 16 1 20 3"
   R.findFirstIn(rec) match {
     case Some(s) => assert(s == rec)
     case None => fail()

   }
  }
}
