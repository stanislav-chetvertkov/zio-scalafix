package fix

import scalafix.v1._
import scala.meta._

class ZioRewrite extends SemanticRule("ZioRewrite") {

  override def fix(implicit doc: SemanticDocument): Patch = {
    println("Tree.syntax: " + doc.tree.syntax)
    println("Tree.structure: " + doc.tree.structure)
    println("Tree.structureLabeled: " + doc.tree.structureLabeled)
    doc.tree.collect {
      case t@q"ZIO.succeed(())" => Patch.replaceTree(t, "ZIO.unit")
    }.asPatch
  }

}
