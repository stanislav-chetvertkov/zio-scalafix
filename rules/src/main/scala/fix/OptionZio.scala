package fix

import scalafix.v1._
import scala.meta._
class OptionZio extends SemanticRule("OptionZio") {
  override def fix(implicit doc: SemanticDocument): Patch = {
    println(doc.tree)

    println("Tree.syntax: " + doc.tree.syntax)
    println("Tree.structure: " + doc.tree.structure)
    println("Tree.structureLabeled: " + doc.tree.structureLabeled)
    doc.tree.collect {
      case t@q"ZIO.succeed(None)" => Patch.replaceTree(t, "ZIO.none")
      case t@q"ZIO.succeed(Some($x))" => Patch.replaceTree(t, q"ZIO.some($x)".toString())
    }.asPatch
  }

}


