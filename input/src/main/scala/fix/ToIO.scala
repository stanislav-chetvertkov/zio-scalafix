/*
rule = ToIO
*/
package fix

import zio.ZIO

object ToIO {
  val io: ZIO[Any, String, Unit] = ZIO.unit
  val task: ZIO[Any, Throwable, Unit] = ZIO.unit
  val rio: ZIO[String, Throwable, Unit] = ZIO.unit
}
