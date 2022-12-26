/*
rule = ZioWhen
*/
package fix

import zio.ZIO

object ZioWhen {
  val cond: Boolean = true
  if (cond) ZIO.succeed("test") else ZIO.unit
}
