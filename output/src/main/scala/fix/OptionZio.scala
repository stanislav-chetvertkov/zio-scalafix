package fix

import zio.ZIO
object OptionZio {
  ZIO.some("test")
  ZIO.none
}
