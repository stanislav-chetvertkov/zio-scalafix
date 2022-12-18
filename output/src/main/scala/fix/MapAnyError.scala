package fix

import zio.ZIO
object MapAnyError {
  ZIO.fromOption(None).orElseFail("failed")
}