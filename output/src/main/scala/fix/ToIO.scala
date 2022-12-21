package fix

import zio._

object ToIO {
  val io: IO[String, Unit] = ZIO.unit
  val task: Task[Unit] = ZIO.unit
  val rio: RIO[String, Unit] = ZIO.unit
  //layers
  val layer: Layer[String, Unit] = ZLayer.fail("fail")
  val urlayer: URLayer[String, Unit] = ZLayer.succeed(())
  val taskLayer: TaskLayer[Unit] = ZLayer.succeed(())
  val ulayer: ULayer[Unit] = ZLayer.succeed(())
  val rlayer: RLayer[String, Unit] = ZLayer.succeed(())
  // & replacement
  val withTest: URIO[String & Int & Boolean, Unit] = ZIO.unit
}
