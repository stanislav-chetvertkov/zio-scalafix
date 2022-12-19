package fix

import zio._
object MapAnyError {
  ZIO.fromOption(None).orElseFail("failed")

  val param: ZIO[Any, String, Unit] = ZIO.succeed(())
  param.orElseFail("failed2")
}