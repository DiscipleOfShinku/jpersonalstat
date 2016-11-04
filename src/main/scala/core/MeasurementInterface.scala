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

  //rewrites single Measurement information (excluding log) or creates new
  def update(m: Measurement)

  //rewrites single log entry or creates new
  def updateLog(l: MeasurementLog)

  //creates new measurement object with given parameters and new id
  protected def create(name: String): Measurement = {
    new Measurement(newId(), name, 0)
  }

  //creates log object with given parameters and new id
  protected def createLog(mid: Int, message: String, date: DateTime) = {
    new MeasurementLog(newLogId(), mid, date, message)
  }

  //increments Measurement state and adds log-record to its history
  def inc(m: Measurement, message: String, date: DateTime) = {
    val l = createLog(m.id, message, date)
    updateLog(l)
    m.value += 1
    update(m)
  }

  //Adds new Measurement with given name
  def add(name: String) = {
      update(create(name))
  }

  //checks if value of measurement equals to the number of log entries
  def checkConsistency(m: Measurement) = m.value == getLog(m).length

  //updates value of measurement to the consistent state
  def refreshValues(m: Measurement) = {
    m.value = getLog(m).length
    update(m)
  }
}
