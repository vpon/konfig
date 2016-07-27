package com.vpon.konfig

import org.scalacheck._
import Prop.forAll
import com.typesafe.config.{ Config, ConfigFactory }
import org.scalatest.{ FlatSpec, Matchers }

import scala.concurrent.duration._

package model {
  sealed trait A
  case class A1(a: Int) extends A
  case class A2(a: Long) extends A
  case class A3(a: String) extends A

  sealed trait B
  case class B1(a: A) extends B
  case class B2(b: Int) extends B

  sealed trait C
  case object C1 extends C
  case class C2(a: A) extends C
  case class C3(b: B) extends C

  case class D(a: A, b: B, c: List[C])
}

object KonfigTest extends Properties("konfig") {
  import com.vpon.konfig.model._

  def parseConfig(c: String): Config = ConfigFactory.parseString(c)

  property("case class derivation") = forAll {
    (a: Int, b: Int, c: Int, d: Int, e: Int) =>
      parseConfig(
        s"""
              d {
                a {
                  type = A1
                  a = $a
                }
                b {
                  type = B1
                  a {
                    type = A2
                    a = $b
                  }
                }
                c = [
                  {
                    type = C1
                  },
                  {
                    type = C2
                    a = {
                      type = A3
                      a = $c
                    }
                  }
                ]
              }
          """
      ).read[D]("d") == D(A1(a), B1(A2(b.toLong)), List(C1, C2(A3(c.toString))))
  }

  implicit val arbiStr = Arbitrary(Gen.alphaStr)

  property("hyphen style key conversion") = forAll {
    (a: String) =>
      KeyStyle.Hyphen.style(a).filter(_.isUpper).isEmpty
  }

}

class HyphenStyleKeySpec extends FlatSpec with Matchers {
  "Hyphen style conversion" should "work" in {
    KeyStyle.Hyphen.style("someKey") should be("some-key")
    KeyStyle.Hyphen.style("some_other_key") should be("some_other_key")
    KeyStyle.Hyphen.style("someDigits123123") should be("some-digits123123")
    KeyStyle.Hyphen.style("AAAAA") should be("a-a-a-a-a")
  }
}

class KeyStyleCustomizableSpec extends FlatSpec with Matchers {
  case class T(ABCDEF: String)
  implicit val keyStyle = KeyStyle.Same
  "Keystyle" should "be customizable" in {
    ConfigFactory.parseString("t { ABCDEF = \"aaaa\" }")
      .read[T]("t") should be(T("aaaa"))
  }
}

class KonfigSpec extends FlatSpec with Matchers {
  "Map reader" should "work" in {
    ConfigFactory.parseString(
      """
        |m {
        |  aaa = bbb
        |  ccc.ddd = eee.fff
        |  a.b.c = 1
        |  a.e.f.g = 2
        |  a.j.k.l = ppp
        |}
      """.stripMargin
    )
      .read[Map[String, String]]("m") should be(Map(
        "aaa" -> "bbb",
        "ccc.ddd" -> "eee.fff",
        "a.b.c" -> "1",
        "a.e.f.g" -> "2",
        "a.j.k.l" -> "ppp"
      ))

    ConfigFactory.parseString("b = [ 1, 2, 3 ]")
      .read[List[Int]]("b") should be(List(1, 2, 3))

    ConfigFactory.parseString("b = [ 1, 2, 3 ]")
      .read[Set[Int]]("b") should be(Set(1, 2, 3))

    ConfigFactory.parseString("b = [ 1, 2, 3 ]")
      .read[Vector[Int]]("b") should be(Vector(1, 2, 3))

    ConfigFactory.parseString("n = 3.14159265358979323846264338327950288")
      .read[BigDecimal]("n") should be(BigDecimal("3.14159265358979323846264338327950288"))

    ConfigFactory.parseString("f = 2.71828182846")
      .read[Double]("f") should be(2.71828182846)

    ConfigFactory.parseString("d = 5 day")
      .read[FiniteDuration]("d") should be(5.days)
  }
}
