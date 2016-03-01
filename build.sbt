name := "SkyModel"
description := "Presentation of patterns to be used for akka business model"
version := "1.0-SNAPSHOT"

scalaVersion := "2.11.7"

javacOptions := Seq("-source", "1.8", "-target", "1.8", "-g")
scalacOptions := Seq("-unchecked", "-deprecation", "-encoding", "utf8", "-Xlint", "-Xfatal-warnings")
scalacOptions in Test ++= Seq("-Yrangepos")

test in assembly := {}
assemblyJarName in assembly := "skymodel.jar"

val akkaV = "2.4.2"
val swingV = "2.11.0-M7"

libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-actor"       % akkaV,
  "com.typesafe.akka" %% "akka-cluster"     % akkaV,
  "com.typesafe.akka" %% "akka-persistence" % akkaV,
  "com.typesafe.akka" %% "akka-persistence-cassandra" % "0.11",

  "org.scala-lang" % "scala-swing" % swingV

)
