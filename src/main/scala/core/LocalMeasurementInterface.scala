package core
import com.github.nscala_time.time.Imports._

/**
  * Created by nekomiko on 04.11.16.
  */
//local interface defined by fixed lists of measurements and logs
class LocalMeasurementInterface(var ms: List[Measurement], var logs: List[MeasurementLog]) extends MeasurementInterface {

  def getById(id: Int): Measurement = {
    val found = ms.filter( m => m.id == id)
    if(found.isEmpty) null
    else found.head
  }
  def getAll(): List[Measurement] = ms
  def getLog(m: Measurement): List[MeasurementLog] = {
    logs.filter( l => l.mid == m.id)
  }
  def getAllLogs(): List[MeasurementLog] = {
    return logs
  }
  protected def newId() = {
    ms.foldLeft(0)( (i,m) => Math.max(i,m.id)) + 1
  }
  protected def newLogId() = {
    logs.foldLeft(0)( (i,l) => Math.max(i,l.id)) + 1
  }

  def update(m: Measurement) = {
    val idx = ms.indexWhere( m0 => m0.id == m.id )
    if(idx != -1) {
      ms = ms.updated(idx,m)
    }
  }
  def updateLog(l: MeasurementLog) = {
    val idx = logs.indexWhere ( l0 => l0.id == l.id )
    if(idx != -1) {
      logs = logs.updated(idx,l)
    }
  }
  def add(name: String): Measurement = {
    val ID = newId()
    val m = new Measurement(ID, name, 0)
    ms ::= m
    return getById(ID)
  }
  def addLog(m: Measurement, date: DateTime, message: String) = {
    val l = new MeasurementLog(newLogId(),m.id, date, message)
    logs ::= l
  }
}
