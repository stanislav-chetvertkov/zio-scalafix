package fix

import zio.ZIO
object ZioUnit {
  ZIO.succeed("test").unit
}