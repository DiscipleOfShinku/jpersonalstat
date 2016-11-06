name := "jpersonal-stat"

version := "1.0"

scalaVersion := "2.12.0"

libraryDependencies += "com.github.nscala-time" %% "nscala-time" % "2.14.0"

libraryDependencies += "junit" % "junit" % "4.10" % "test"

libraryDependencies += "org.scalatest" %% "scalatest" % "3.0.0" % "test"

libraryDependencies += "org.xerial" % "sqlite-jdbc" % "3.8.11.2"

testOptions in Test += Tests.Argument("-oF")
