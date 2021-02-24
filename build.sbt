name := """Manga"""
organization := "com.example"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.13.3"
lazy val scalatestplusVersion = "5.0.0"
lazy val slickVersion = "5.0.0"
lazy val postgresqlVersion = "42.2.15"

libraryDependencies ++= Seq(
    guice,
    "org.scalatestplus.play" %% "scalatestplus-play" % scalatestplusVersion % Test,
    "com.typesafe.play" %% "play-slick" % slickVersion,
    "com.typesafe.play" %% "play-slick-evolutions" % slickVersion,
    "org.postgresql" % "postgresql" % postgresqlVersion
)

// Adds additional packages into Twirl
//TwirlKeys.templateImports += "com.example.controllers._"

// Adds additional packages into conf/routes
// play.sbt.routes.RoutesKeys.routesImport += "com.example.binders._"
