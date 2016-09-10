package com.vpon.konfig

import com.twitter.util.{ Duration, StorageUnit }
import com.typesafe.config.Config

package object twitterutil {
  implicit object durationReader extends ConfigReader[Duration] {
    override def read(c: Config, path: String) = Duration.fromNanoseconds(c.getDuration(path).toNanos)
  }

  implicit object storageUnitReader extends ConfigReader[StorageUnit] {
    override def read(c: Config, path: String) = StorageUnit.fromBytes(c.getBytes(path))
  }
}