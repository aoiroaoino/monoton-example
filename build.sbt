import Dependencies._

ThisBuild / scalaVersion     := "2.13.0"
ThisBuild / version          := "0.1.0-SNAPSHOT"
ThisBuild / organization     := "com.example"
ThisBuild / organizationName := "example"

lazy val root = (project in file("."))
  .settings(
    name := "monoton-example",
    libraryDependencies += scalaTest % Test,
    libraryDependencies += "io.circe" %% "circe-generic" % "0.12.0-RC4",
  )
  .enablePlugins(MonotonPlugin)
