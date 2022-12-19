/*
rule = MapAnyError
*/
package fix

import zio._
object MapAnyError {
  ZIO.fromOption(None).mapError(_ => "failed")

  val param: ZIO[Any, String, Unit] = ZIO.succeed(())
  param.mapError(_ => "failed2")
}