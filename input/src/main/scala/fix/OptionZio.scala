/*
rule = OptionZio
*/
package fix

import zio.ZIO
object OptionZio {
  ZIO.succeed(Some("test"))
  ZIO.succeed(None)
}