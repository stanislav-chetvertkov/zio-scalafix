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

## Rules

#### ToIO
Replaces `ZIO` and `ZLayer` types with the appropriate aliases when applicable.
For `R`(environment) type parameters changes `with` to `&` 

```scala
val withTest: ZIO[String with Int with Boolean, Nothing, Unit] = ZIO.unit
```

becomes

```scala
val withTest: URIO[String & Int & Boolean, Unit] = ZIO.unit
```

#### MapAnyError
Calling `mapError` and ignoring the parameter is replaced with `orElseFail`

```scala
zio.mapError(_ => "failed")
```

becomes

```scala
zio.orElseFail("failed")
```

#### ZioUnit
The following expressions can be simplified to .unit:

```scala
x *> ZIO.unit  -> x.unit
x.map(_ => ()) -> x.unit
```

#### ZioIgnore

```scala
x.catchAll(_ => ZIO.unit)                  -> x.ignore
x.foldCause(_ => (), _ => ())              -> x.ignore
x.foldCauseM(_ => ZIO.unit, _ => ZIO.unit) -> x.ignore
```
