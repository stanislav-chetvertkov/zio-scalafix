# Scalafix rules for ZIO rewrite

To develop rule:
```
sbt ~tests/test
# edit rules/src/main/scala/fix/ZioRewrite.scala
```


```scala
ThisBuild /  scalafixDependencies += "io.github.schetvertkov" %% "zio-scalafix-rules" % "<version>"
```