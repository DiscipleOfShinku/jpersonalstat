package core

import com.github.nscala_time.time.Imports._


/**
  * Created by nekomiko on 04.11.16.
  */
trait MeasurementInterface {
  //retrieves requested measurement by its id using current interface implementation
  def getById(id: Int): Measurement

  def getAll(): List[Measurement]

  def getLast(): Int = {
    val ms = getAll()
    ms.foldLeft(0)( (i,m) => Math.max(i,m.id))
  }

  protected def newId(): Int

  protected def newLogId(): Int

  def getLog(m: Measurement): List[MeasurementLog]

  //rewrites single Measurement information (excluding log) if it exists
  def update(m: Measurement)

  //rewrites single log entry if it exists
  def updateLog(l: MeasurementLog)

  //creates log object with given parameters and new id
  protected def createLog(mid: Int, message: String, date: DateTime) = {
    new MeasurementLog(newLogId(), mid, date, message)
  }

  //increments Measurement state and adds log-record to its history
  def inc(m: Measurement, message: String, date: DateTime) = {
    addLog(m, date, message)
    m.value += 1
    update(m)
  }

  //Adds new Measurement with given name
  def add(name: String)

  //adds new log entry with given parameters
  //should not be used directly, use inc instead
  protected def addLog(m: Measurement, date: DateTime, message: String)

  //checks if value of measurement equals to the number of log entries
  def checkConsistency(m: Measurement) = m.value == getLog(m).length

  //updates value of measurement to the consistent state
  def refreshValues(m: Measurement) = {
    m.value = getLog(m).length
    update(m)
  }
}
