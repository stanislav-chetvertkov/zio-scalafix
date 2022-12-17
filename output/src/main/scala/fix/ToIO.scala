package fix

import zio.{IO, Task, ZIO}

object ToIO {
  val action: IO[String, Unit] = ZIO.unit
  val action2: Task[Unit] = ZIO.unit
}
