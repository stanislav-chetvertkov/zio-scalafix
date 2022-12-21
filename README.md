# Scalafix rules for ZIO rewrite

To develop rule:
```
sbt ~tests/test
# edit rules/src/main/scala/fix/ZioRewrite.scala
```

* todo: add a rule for 'zio &' in type signatures



```scala
ThisBuild /  scalafixDependencies += "io.github.schetvertkov" %% "zio-scalafix-rules" % "<version>"
```