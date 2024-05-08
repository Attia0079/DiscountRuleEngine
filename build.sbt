import sbt.Keys.libraryDependencies

import scala.collection.Seq

ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "2.13.14"

lazy val root = (project in file(".")).settings(
    name := "DREV2",
  libraryDependencies += "org.postgresql" % "postgresql" % "42.2.23",
  libraryDependencies += "com.typesafe.scala-logging" %% "scala-logging" % "3.9.4",
  libraryDependencies += "ch.qos.logback" % "logback-classic" % "1.2.3",
)
