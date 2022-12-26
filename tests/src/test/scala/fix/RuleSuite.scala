package fix

import scalafix.testkit._
import org.scalatest.FunSuiteLike

class RuleSuite extends AbstractSemanticRuleSuite with FunSuiteLike {

  testsToRun.filter(_.path.testName.contains("ZioIgnore")).foreach(runOn)
//  runAllTests()
}
