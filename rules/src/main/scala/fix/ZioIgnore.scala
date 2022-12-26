package fix

import scalafix.v1._
import scala.meta._
class ZioIgnore extends SemanticRule("ZioIgnore") {
  override def fix(implicit doc: SemanticDocument): Patch = {
    val ZioCatchAllMatcher = SymbolMatcher.exact("zio/ZIO#catchAll().")
    val ZioFoldCause = SymbolMatcher.exact("zio/ZIO#foldCause().")

    // x.foldCause(_ => (), _ => ())              -> x.ignore

    doc.tree.collect {
      case t@Term.Apply(fun, args) =>
        if (ZioCatchAllMatcher.matches(fun.symbol)) {
          t match {
            case q"$c.catchAll(_ => ZIO.unit)" =>
              Patch.replaceTree(t, q"$c.ignore".toString())
            case _ =>
              Patch.empty
          }
        } else if (ZioFoldCause.matches(fun.symbol)) {
          t match {
            case q"$c.foldCause(_ => (), _ => ())" =>
              Patch.replaceTree(t, q"$c.ignore".toString())
            case _ =>
              Patch.empty
          }
        } else {
          Patch.empty
        }
    }.asPatch
  }

}