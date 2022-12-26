package fix

import zio.ZIO
object ZioUnit {
  ZIO.succeed("test").unit
  ZIO.succeed("test2").unit
  val test2 = ZIO.succeed("test2")
  test2.unit
}
