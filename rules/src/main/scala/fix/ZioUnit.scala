package fix

import scalafix.v1._
import scala.meta._
class ZioUnit extends SemanticRule("ZioUnit") {
  override def fix(implicit doc: SemanticDocument): Patch = {
//    println("Tree.syntax: " + doc.tree.syntax)
//    println("Tree.structure: " + doc.tree.structure)
//    println("Tree.structureLabeled: " + doc.tree.structureLabeled)

    //  List(
    //   Term.ApplyInfix(
    //     Term.Apply(
    //       Term.Select(Term.Name("ZIO"), Term.Name("succeed")),
    //       List(Lit.String("test"))
    //     ),
    //     Term.Name("*>"),
    //     List(),
    //     List(Term.Select(Term.Name("ZIO"), Term.Name("unit")))
    //   )
    // ),

    val ZioMatcher = SymbolMatcher.exact("zio/ZIO#")
    val AliasForApply = SymbolMatcher.exact("zio/ZIO#`*>`().")

    doc.tree.collect {
      case t@ Term.ApplyInfix(app, applyOp, _, List(Term.Select(Term.Name("ZIO"), Term.Name("unit")))) =>
        app.symbol.info.map(_.signature) match {
          case Some(MethodSignature(_, _, c)) =>
            c match {
              case TypeRef(_, symbol, _) =>
                if (ZioMatcher.matches(symbol)){
                  if (AliasForApply.matches(applyOp.symbol)) {
                    Patch.replaceTree(t, q"$app.unit".toString())
                  } else {
                    Patch.empty
                  }
                } else {
                  Patch.empty
                }
              case error =>
                Patch.empty
            }
        }
    }.asPatch
  }

}