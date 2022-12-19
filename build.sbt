lazy val V = _root_.scalafix.sbt.BuildInfo

lazy val rulesCrossVersions = Seq(V.scala213, V.scala212, V.scala211)
lazy val scala3Version = "3.1.1"

lazy val commonSettings = Seq(
  addCompilerPlugin(scalafixSemanticdb),
  addCompilerPlugin("org.scalameta" % "semanticdb-scalac" % "4.7.0" cross CrossVersion.full),
  scalacOptions += "-Yrangepos",           // required by SemanticDB compiler plugin
//  scalacOptions += "-Ywarn-unused-import", // required by SemanticDB compiler plugin
)

//ThisBuild / scalaVersion := V.scala212

addCompilerPlugin("org.scalameta" % "semanticdb-scalac" % "4.7.0" cross CrossVersion.full)
ThisBuild / scalacOptions += "-Yrangepos"

inThisBuild(
  List(
    organization := "io.github.schetvertkov",
    licenses := List(
      "Apache-2.0" -> url("http://www.apache.org/licenses/LICENSE-2.0")
    ),
    developers := List(
      Developer(
        "stanislav-che8",
        "Stanislav Chetvertkov",
        "stanislav.chetvertkov@gmail.com",
        url("https://github.com/stanislav-chetvertkov")
      )
    ),
    semanticdbEnabled := true,
    semanticdbIncludeInJar := true,
    semanticdbVersion := scalafixSemanticdb.revision,
//    sonatypeCredentialHost := "s01.oss.sonatype.org",
//    sonatypeRepository := "https://s01.oss.sonatype.org/service/local",
//    sonatypeProfileName := "io.github.schetvertkov",
//    scalacOptions ++= List("-Yrangepos"),
//    sonatypeSnapshotResolver := {
//      MavenRepository(
//        "s01-sonatype-snapshots",
//        s"https://${sonatypeCredentialHost.value}/content/repositories/snapshots"
//      )
//    },
//    sonatypeStagingResolver := {
//      MavenRepository(
//        "s01-sonatype-staging",
//        s"https://${sonatypeCredentialHost.value}/service/local/staging/deploy/maven2"
//      )
//    }
  )
)

lazy val `zio-scalafix-rules` = (project in file("."))
  .aggregate(
    rules.projectRefs ++
      input.projectRefs ++
      output.projectRefs ++
      tests.projectRefs: _*
  )
  .settings(
    publish / skip := true
  )

lazy val rules = projectMatrix
  .settings(
    moduleName := "zio-scalafix-rules",
    libraryDependencies += "ch.epfl.scala" %% "scalafix-core" % V.scalafixVersion,
//    libraryDependencies += "org.scalameta" %% "semanticdb" % "4.7.0",
    libraryDependencies += "dev.zio" %% "zio" % "2.0.5",
    commonSettings
  )
  .defaultAxes(VirtualAxis.jvm)
  .jvmPlatform(rulesCrossVersions)

lazy val input = projectMatrix
  .settings(
    publish / skip := true,
    libraryDependencies += "dev.zio" %% "zio" % "2.0.5",
    commonSettings
  )
  .defaultAxes(VirtualAxis.jvm)
  .jvmPlatform(scalaVersions = rulesCrossVersions)// :+ scala3Version)

lazy val output = projectMatrix
  .settings(
    publish / skip := true,
    libraryDependencies += "dev.zio" %% "zio" % "2.0.5"
  )
  .defaultAxes(VirtualAxis.jvm)
  .jvmPlatform(scalaVersions = rulesCrossVersions)// :+ scala3Version)

lazy val testsAggregate = Project("tests", file("target/testsAggregate"))
  .aggregate(tests.projectRefs: _*)
  .settings(
    publish / skip := true,
    commonSettings
  )

lazy val tests = projectMatrix
  .settings(
    libraryDependencies += "ch.epfl.scala" % "scalafix-testkit" % V.scalafixVersion % Test cross CrossVersion.full,
    commonSettings,
    publish / skip := true,
    scalafixTestkitOutputSourceDirectories := TargetAxis.resolve(output, Compile / unmanagedSourceDirectories).value,
    scalafixTestkitInputSourceDirectories := TargetAxis.resolve(input, Compile / unmanagedSourceDirectories).value,
    scalafixTestkitInputClasspath := TargetAxis.resolve(input, Compile / fullClasspath).value,
    scalafixTestkitInputScalacOptions := TargetAxis.resolve(input, Compile / scalacOptions).value,
    scalafixTestkitInputScalaVersion := TargetAxis.resolve(input, Compile / scalaVersion).value
  )
  .defaultAxes(
    rulesCrossVersions.map(VirtualAxis.scalaABIVersion) :+ VirtualAxis.jvm: _*
  )
//  .jvmPlatform(
//    scalaVersions = Seq(V.scala212),
//    axisValues = Seq(TargetAxis(scala3Version)),
//    settings = Seq()
//  )
  .jvmPlatform(
    scalaVersions = Seq(V.scala213),
    axisValues = Seq(TargetAxis(V.scala213)),
    settings = Seq()
  )
  .jvmPlatform(
    scalaVersions = Seq(V.scala212),
    axisValues = Seq(TargetAxis(V.scala212)),
    settings = Seq()
  )
//  .jvmPlatform(
//    scalaVersions = Seq(V.scala211),
//    axisValues = Seq(TargetAxis(V.scala211)),
//    settings = Seq()
//  )
  .dependsOn(rules)
  .enablePlugins(ScalafixTestkitPlugin)
