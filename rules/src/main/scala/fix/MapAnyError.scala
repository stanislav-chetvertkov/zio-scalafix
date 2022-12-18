package fix

import scalafix.v1._
import scala.meta._
class MapAnyError extends SemanticRule("MapAnyError") {
  override def fix(implicit doc: SemanticDocument): Patch = {
    println(doc.tree)



    /*
    stats = List(
      Term.Apply(
        fun = Term.Select(
          qual = Term.Apply(
            fun = Term.Select(
              qual = Term.Name("ZIO"),
              name = Term.Name("fromOption")
            ),
            args = List(Term.Name("None"))
          ),
          name = Term.Name("mapError")
        ),
        args = List(
          Term.Function(
            params = List(
              Term.Param(
                mods = List(),
                name = Name.Anonymous,
                decltpe = None,
                default = None
              )
            ),
            body = Lit.String("failed")
          )
        )
      )
     */

    println("Tree.syntax: " + doc.tree.syntax)
    println("Tree.structure: " + doc.tree.structure)
    println("Tree.structureLabeled: " + doc.tree.structureLabeled)
    doc.tree.collect {
      case t@ q"$c.mapError(_ => $o)" =>
        println(o)
        Patch.replaceTree(t, q"$c.orElseFail($o)".toString())
//      case t@Term.Apply(Term.Select(_, Term.Name("mapError")), x) =>
//        println(t)
//        Patch.empty
//
//      case t@Term.Apply(x) =>
//        println(t)
//        Patch.empty
    }.asPatch
  }

}