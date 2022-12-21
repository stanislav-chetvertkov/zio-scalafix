/*
rule = ToIO
*/
package fix

import zio._

object ToIO {
  val io: ZIO[Any, String, Unit] = ZIO.unit
  val task: ZIO[Any, Throwable, Unit] = ZIO.unit
  val rio: ZIO[String, Throwable, Unit] = ZIO.unit
  //layers
  val layer: ZLayer[Any, String, Unit] = ZLayer.fail("fail")
  val urlayer: ZLayer[String, Nothing, Unit] = ZLayer.succeed(())
  val taskLayer: ZLayer[Any, Throwable, Unit] = ZLayer.succeed(())
  val ulayer: ZLayer[Any, Nothing, Unit] = ZLayer.succeed(())
  val rlayer: ZLayer[String, Throwable, Unit] = ZLayer.succeed(())
  // & replacement
  val withTest: ZIO[String with Int with Boolean, Nothing, Unit] = ZIO.unit
}
