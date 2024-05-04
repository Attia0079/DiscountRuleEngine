ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "2.13.14"

lazy val root = (project in file("."))
  .settings(
    name := "final-project"
  )
// Add scala-parallel-collections dependency
libraryDependencies += "org.scala-lang.modules" %% "scala-parallel-collections" % "1.0.4"

// Add Spark dependencies
libraryDependencies ++= Seq(
  "org.apache.spark" %% "spark-core" % "3.4.0",
  "org.apache.spark" %% "spark-sql" % "3.4.0"
)
//libraryDependencies ++= Seq(
//  "org.slf4j" % "slf4j-api" % "1.7.32",
//  "ch.qos.logback" % "logback-classic" % "1.2.6"
//)
//libraryDependencies += "com.typesafe.scala-logging" %% "scala-logging" % "3.9.4",
//libraryDependencies += "ch.qos.logback" % "logback-classic" % "1.2.3"


libraryDependencies ++= Seq(
  "org.slf4j" % "slf4j-api" % "1.7.32",
  "ch.qos.logback" % "logback-classic" % "1.2.6"
)

