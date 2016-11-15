/**
  * Created by DiscipleOfShinku on 11/15/2016.
  */


import com.github.nscala_time.time.Imports._
import org.junit.runner.RunWith
import org.scalatest.FunSuite
import org.scalatest.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class CmdUI extends FunSuite {
  def testCmdUI(iface: cmdui.CmdUI): Unit = {
    cmdui.CmdUI.main(Array("-h"))
    cmdui.CmdUI.main(Array("-s", "-A"))
    cmdui.CmdUI.main(Array("-s", "-A", "-l", "1"))
    cmdui.CmdUI.main(Array("-s", "-m", "1"))
    cmdui.CmdUI.main(Array("-s", "-l", "1"))
    cmdui.CmdUI.main(Array("-a", "test"))
    cmdui.CmdUI.main(Array("-i", "1"))
  }

  test("cmdUI tests") {
    val cmdUI = new cmdui.CmdUI
    testCmdUI(cmdUI)
  }

}
