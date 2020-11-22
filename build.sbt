val commonScalacOptions = Seq(
  "-feature",
  "-deprecation",
  "-unchecked",
  "-target:jvm-1.8",
  "-encoding",
  "UTF-8",
  "-Ywarn-unused:implicits",
  "-Ywarn-unused:imports",
  "-Ywarn-unused:locals",
  "-Ywarn-unused:params",
  "-Ywarn-unused:patvars",
  "-Ywarn-unused:privates",
  "-Ywarn-value-discard",
  "-language:higherKinds",
  "-language:postfixOps",
  "-unchecked",
  "-Xfatal-warnings"
)

scalaVersion := "2.13.2"

val commonSettings = Seq(
  parallelExecution in Test := false,
  fork in Test := true,
  parallelExecution in IntegrationTest := false,
  fork in IntegrationTest := false,
  addCompilerPlugin("com.olegpy" %% "better-monadic-for" % "0.3.1")
)

lazy val core = (project in file("core"))
  .settings(commonSettings)
  .settings(
    name := "informant-core"
  )

val scala2_12_options = List(
  "-Yno-adapted-args",
  "-Ypartial-unification",
  "-Xmax-classfile-name",
  "100"
)

lazy val root = (project in file("."))
  .settings(name := "informant-core")
  .aggregate(core)

cancelable in Global := true
