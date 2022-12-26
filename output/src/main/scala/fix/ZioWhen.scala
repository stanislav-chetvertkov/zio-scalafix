package fix

import zio.ZIO

object ZioWhen {
  val cond: Boolean = true
  ZIO.succeed("test").when(cond)
}
