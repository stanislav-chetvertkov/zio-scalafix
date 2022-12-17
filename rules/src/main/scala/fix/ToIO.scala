package fix

import scalafix.v1._
import scala.meta._

//fixme: rename to AliasTypesForZIO
//todo: create a new rule for 'AliasTypesForZLayers'

class ToIO extends SemanticRule("ToIO") {
  override def fix(implicit doc: SemanticDocument): Patch = {
    val imports = doc.tree.collect {
      case t@ q"import zio.ZIO" =>
        Patch.addLeft(t, "import zio.IO\n")
    }

    val zioPatches = doc.tree.collect {
      case t@Type.Apply(Type.Name("ZIO"), List(Type.Name("Any"), Type.Name("Nothing"), b)) =>
        Patch.replaceTree(t, Type.Apply(Type.Name("UIO"), List(b)).toString())
      case t@Type.Apply(Type.Name("ZIO"), List(r, Type.Name("Nothing"), b)) =>
        Patch.replaceTree(t, Type.Apply(Type.Name("URIO"), List(r, b)).toString())
      case t@Type.Apply(Type.Name("ZIO"), List(Type.Name("Any"), Type.Name("Throwable"), b)) =>
        Patch.replaceTree(t, Type.Apply(Type.Name("Task"), List(b)).toString())
      case t@Type.Apply(Type.Name("ZIO"), List(r, Type.Name("Throwable"), b)) =>
        Patch.replaceTree(t, Type.Apply(Type.Name("RIO"), List(r, b)).toString())
      case t@Type.Apply(Type.Name("ZIO"), List(Type.Name("Any"), e, b)) =>
        Patch.replaceTree(t, Type.Apply(Type.Name("IO"), List(e, b)).toString())
    }

    (zioPatches ++ imports).asPatch
  }

}