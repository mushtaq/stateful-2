import sbt._

object Libs {
  val `scalatest`                = "org.scalatest"          %% "scalatest"                % "3.0.8"
  val `scala-async`              = "org.scala-lang.modules" %% "scala-async"              % "0.10.0"
  val `play-json`                = "com.typesafe.play"      %% "play-json"                % "2.7.4"
  val `akka-http-play-json`      = "de.heikoseeberger"      %% "akka-http-play-json"      % "1.27.0"
  val `play-json-derived-codecs` = "org.julienrf"           %% "play-json-derived-codecs" % "6.0.0"
  val `enumeratum`               = "com.beachape"           %% "enumeratum"               % "1.5.13"
  val `enumeratum-play-json`     = "com.beachape"           %% "enumeratum-play-json"     % "1.5.16"
  val `ammonite`                 = "com.lihaoyi"            % "ammonite"                  % "1.6.9" cross CrossVersion.full
}

object Akka {
  val Version                 = "2.5.24"
  val `akka-stream-typed`     = "com.typesafe.akka" %% "akka-stream-typed" % Version
  val `akka-distributed-data` = "com.typesafe.akka" %% "akka-distributed-data" % Version
  val `akka-cluster-typed`    = "com.typesafe.akka" %% "akka-cluster-typed" % Version
  val `akka-slf4j`            = "com.typesafe.akka" %% "akka-slf4j" % Version
}

object AkkaHttp {
  val Version     = "10.1.9"
  val `akka-http` = "com.typesafe.akka" %% "akka-http" % Version
}
