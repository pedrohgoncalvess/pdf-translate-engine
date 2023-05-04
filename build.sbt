ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "2.13.10"

lazy val root = (project in file("."))
  .settings(
    name := "translated-scalability"
  )

libraryDependencies ++= Seq(
  "com.lihaoyi" %% "requests" % "0.8.0", //REQUESTS
  "org.jsoup" % "jsoup" % "1.14.3", //HTML PARSER
  "org.apache.pdfbox" % "pdfbox" % "2.0.24" //PDF
)
