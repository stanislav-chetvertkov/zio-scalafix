resolvers ++= Resolver.sonatypeOssRepos("releases")

addSbtPlugin("ch.epfl.scala" % "sbt-scalafix" % "0.10.4")
addSbtPlugin("com.eed3si9n" % "sbt-projectmatrix" % "0.9.0")
addSbtPlugin("org.scalameta" % "sbt-scalafmt" % "2.4.6")
