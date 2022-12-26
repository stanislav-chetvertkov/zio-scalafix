package fix

import scalafix.v1._
import scala.meta._
class ZioWhen extends SemanticRule("ZioWhen") {

  //   Term.If(
  //      cond = Term.Name("cond"),
  //      thenp = Term.Apply(
  //        fun = Term.Select(
  //          qual = Term.Name("ZIO"),
  //          name = Term.Name("succeed")
  //        ),
  //        args = List(Lit.String("test"))
  //      ),
  //      elsep = Term.Select(
  //        qual = Term.Name("ZIO"),
  //        name = Term.Name("unit")
  //      ),
  //      mods = List()
  //    )

  override def fix(implicit doc: SemanticDocument): Patch = {
    println("Tree.syntax: " + doc.tree.syntax)
    println("Tree.structure: " + doc.tree.structure)
    println("Tree.structureLabeled: " + doc.tree.structureLabeled)


    //if (someCond) x else IO.unit  -> x.when(someCond)
    doc.tree.collect {
      case t@ q"if ($cond) $a else $b" =>
        println(a.symbol.info)
        println(b.symbol.info)

        Patch.replaceTree(t, q"$a.when($cond)".toString())
//      case t@ Term.If(p1, p2, p3) =>
//        println(p1.symbol.info)
//        println(p2.symbol.info)
//        println(p3.symbol.info)
//        Patch.empty
    }.asPatch
  }

}