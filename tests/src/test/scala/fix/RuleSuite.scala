package fix

import scalafix.testkit._
import org.scalatest.FunSuiteLike

class RuleSuite extends AbstractSemanticRuleSuite with FunSuiteLike {

//  testsToRun.filter(_.path.testName.contains("ZioWhen")).foreach(runOn)
  runAllTests()
}
