name := "CSYE7200_Scala_Project_Team4"

version := "1.0"

scalaVersion := "2.12.4"

lazy val a = (project in file("CSVKafka"))

lazy val b = (project in file("play-try"))

lazy val root = (project in file(".")).aggregate(a, b)

parallelExecution in Test := false
