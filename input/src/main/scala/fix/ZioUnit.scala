/*
rule = ZioUnit
*/
package fix

import zio.ZIO
object ZioUnit {
  ZIO.succeed("test") *> ZIO.unit
  ZIO.succeed("test2").map(_ => ())
  val test2 = ZIO.succeed("test2")
  test2.map(_ => ())
}
