/**
  * Created by nekomiko on 04.11.16.
  */


import org.scalatest.FunSuite

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner

//import org.scalatest._

import com.github.nscala_time.time.Imports._

@RunWith(classOf[JUnitRunner])
class MeasurementInterface extends FunSuite {
  def testInterface(iface: core.MeasurementInterface): Unit = {
    val mCount = iface.getAll().length
    iface.add("Zero counter")
    assert(iface.getAll().length == mCount + 1)
    val m0 = iface.getById(iface.getLast())
    assert(m0.name == "Zero counter")
    assert(m0.value == 0)
    assert(iface.checkConsistency(m0))

    iface.add("Second counter")
    val m1 = iface.getById(iface.getLast())
    assert(m1.name == "Second counter")
    assert(m1.value == 0)
    assert(iface.checkConsistency(m1))

    iface.inc(m0,"First event of zero type", DateTime.now)
    assert(m0.value == 1)
    assert(iface.checkConsistency(m0))

    iface.inc(m0,"Second event of zero type", DateTime.now)
    assert(m0.value == 2)
    assert(iface.checkConsistency(m0))

    iface.inc(m1,"First event of first type", DateTime.now)
    assert(m1.value == 1)
    assert(iface.checkConsistency(m1))

    iface.inc(m0,"Third event of zero type", DateTime.now)
    assert(m0.value == 3)
    assert(iface.checkConsistency(m0))

    iface.inc(m1,"Second event of first type", DateTime.now)
    assert(m1.value == 2)
    assert(iface.checkConsistency(m1))
  }

  test("LocalMeasurementInterface tests") {
    val localInterface = new core.LocalMeasurementInterface(List(),List())
    testInterface(localInterface)
  }

}
