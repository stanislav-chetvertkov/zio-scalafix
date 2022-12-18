/*
rule = MapAnyError
*/
package fix

import zio.ZIO
object MapAnyError {
  ZIO.fromOption(None).mapError(_ => "failed")
}