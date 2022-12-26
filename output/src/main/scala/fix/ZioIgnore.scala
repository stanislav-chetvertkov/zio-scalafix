package fix

import zio.ZIO
object ZioIgnore {
  val zio = ZIO.fail("error")
  zio.ignore

  ZIO.fail("error2").ignore
}

