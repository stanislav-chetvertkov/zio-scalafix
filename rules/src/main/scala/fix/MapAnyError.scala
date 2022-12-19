package fix

import scalafix.v1._
import scala.meta._
class MapAnyError extends SemanticRule("MapAnyError") {
  override def fix(implicit doc: SemanticDocument): Patch = {
    val MapErrorAlias = SymbolMatcher.exact("zio/ZIO#mapError().")

    doc.tree.collect {
      case t@ Term.Apply(fun, args) =>
        if (MapErrorAlias.matches(fun.symbol)) {
          t match {
            case q"$c.mapError(_ => $o)" =>
              Patch.replaceTree(t, q"$c.orElseFail($o)".toString())
            case _ =>
              Patch.empty
          }
        } else {
          Patch.empty
        }
    }.asPatch
  }

}