package fix

import scalafix.v1._
import scala.meta._

//fixme: rename to AliasTypesForZIO
class ToIO extends SemanticRule("ToIO") {
  override def fix(implicit doc: SemanticDocument): Patch = {

    //doc.tree.collect {
    //  case future @ Importee.Name(Name("Future")) =>
    //    Patch.removeImportee(future)
    //}.showDiff()

//    val CompanianAliases = SymbolMatcher.exact("zio/IO.", "zio/UIO.", "zio/URIO.", "zio/Task.", "zio/RIO.")
//
//    val UIOAlias = SymbolMatcher.exact("zio/UIO.")
//    val TaskAlias = SymbolMatcher.exact("zio/Task.")
//    val ZIOAlias = SymbolMatcher.exact("zio/ZIO#")
//    val URIOAlias = SymbolMatcher.exact("zio/URIO#")
//    val RIOAlias: SymbolMatcher = SymbolMatcher.exact("zio/RIO#")

    val imports = doc.tree.collect {
      case t@ q"import zio.ZIO" =>
        Patch.addLeft(t, "import zio.IO\n")
    }

    def replaceAmp(in: Type): Type = {
      in match {
        case t"$r1 with $r2" =>
          t"${replaceAmp(r1)} & $r2"
        case _ =>
          in
      }
    }

    val patchZIO: PartialFunction[Tree, Patch] = {
      case t@Type.Apply(Type.Name("ZIO"), List(Type.Name("Any"), Type.Name("Nothing"), b)) =>
        Patch.replaceTree(t, Type.Apply(Type.Name("UIO"), List(b)).toString())
      case t@Type.Apply(Type.Name("ZIO"), List(r, Type.Name("Nothing"), b)) =>
        Patch.replaceTree(t, Type.Apply(Type.Name("URIO"), List(replaceAmp(r), b)).toString())
      case t@Type.Apply(Type.Name("ZIO"), List(Type.Name("Any"), Type.Name("Throwable"), b)) =>
        Patch.replaceTree(t, Type.Apply(Type.Name("Task"), List(b)).toString())
      case t@Type.Apply(Type.Name("ZIO"), List(r, Type.Name("Throwable"), b)) =>
        Patch.replaceTree(t, Type.Apply(Type.Name("RIO"), List(replaceAmp(r), b)).toString())
      case t@Type.Apply(Type.Name("ZIO"), List(Type.Name("Any"), e, b)) =>
        Patch.replaceTree(t, Type.Apply(Type.Name("IO"), List(e, b)).toString())
    }

    val patchLayer: PartialFunction[Tree, Patch]  = {
      case t@Type.Apply(Type.Name("ZLayer"), List(Type.Name("Any"), Type.Name("Nothing"), b)) =>
        Patch.replaceTree(t, Type.Apply(Type.Name("ULayer"), List(b)).toString())
      case t@Type.Apply(Type.Name("ZLayer"), List(r, Type.Name("Nothing"), b)) =>
        Patch.replaceTree(t, Type.Apply(Type.Name("URLayer"), List(replaceAmp(r), b)).toString())
      case t@Type.Apply(Type.Name("ZLayer"), List(Type.Name("Any"), Type.Name("Throwable"), b)) =>
        Patch.replaceTree(t, Type.Apply(Type.Name("TaskLayer"), List(b)).toString())
      case t@Type.Apply(Type.Name("ZLayer"), List(r, Type.Name("Throwable"), b)) =>
        Patch.replaceTree(t, Type.Apply(Type.Name("RLayer"), List(replaceAmp(r), b)).toString())
      case t@Type.Apply(Type.Name("ZLayer"), List(Type.Name("Any"), e, b)) =>
        Patch.replaceTree(t, Type.Apply(Type.Name("Layer"), List(e, b)).toString())
    }

    val zioPatches = doc.tree.collect(patchZIO orElse patchLayer)

    (zioPatches ++ imports).asPatch
  }

}