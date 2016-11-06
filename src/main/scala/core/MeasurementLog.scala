package core
import com.github.nscala_time.time.Imports._



/**
  * Created by nekomiko on 04.11.16.
  */
class MeasurementLog(val id: Int, val mid: Int, var date: DateTime, var message: String) {
  override def toString = id +"(" + mid + ")  [" + date + "]  " + message

}
