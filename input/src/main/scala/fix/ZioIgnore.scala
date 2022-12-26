/*
rule = ZioIgnore
*/
package fix

import zio.ZIO
object ZioIgnore {
  val zio = ZIO.fail("error")
  zio.catchAll(_ => ZIO.unit)

  ZIO.fail("error2").foldCause(_ => (), _ => ())
}