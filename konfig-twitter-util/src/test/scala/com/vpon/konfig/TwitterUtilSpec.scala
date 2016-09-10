package com.vpon.konfig

import com.twitter.conversions.time._
import com.twitter.conversions.storage._
import com.twitter.util.{ Duration, StorageUnit }
import com.typesafe.config.ConfigFactory
import org.scalatest.{ Matchers, WordSpecLike }

import com.vpon.konfig.twitterutil._

class TwitterUtilSpec extends Matchers with WordSpecLike {
  "duration reader" should {
    "work" in {
      ConfigFactory.parseString("d = 300 ms").read[Duration]("d") should be(300.millis)
      ConfigFactory.parseString("d = 125 days").read[Duration]("d") should be(125.days)
    }
  }

  "storage unit reader" should {
    "work" in {
      ConfigFactory.parseString("s = 876 k").read[StorageUnit]("s") should be(876.kilobytes)
      ConfigFactory.parseString("s = 50 g").read[StorageUnit]("s") should be(50.gigabytes)
    }
  }
}
