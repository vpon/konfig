package com.example

import com.typesafe.config.ConfigFactory

object Config {
  case class AppConfig(
    kafka: ProducerConfig,
    http: WebServerConfig,
    admin: WebServerConfig,
    db: DatabaseConfig
  )

  case class ProducerConfig(
    topic: String,
    properties: Map[String, String]
  )

  case class WebServerConfig(
    host: Option[String],
    port: Int
  )

  sealed trait DatabaseConfig
  case class PostgresConfig(connStr: String, db: String) extends DatabaseConfig
  case class MysqlConfig(connStr: String, db: String) extends DatabaseConfig

  def main(args: Array[String]): Unit = {
    import com.vpon.konfig._
    println(ConfigFactory.load().resolve().read[AppConfig]("my-example-app"))
  }
}
