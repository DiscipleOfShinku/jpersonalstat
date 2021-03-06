package core

import java.sql.Connection
import java.sql.ResultSet

import com.github.nscala_time.time.Imports._
/**
  * Created by nekomiko on 04.11.16.
  */
class SQLiteMeasurementInterface(val con: Connection) extends MeasurementInterface {

  def getById(id: Int): Measurement = {
    val query = "SELECT * FROM measurements WHERE id=?;"
    val pSt = con.prepareStatement(query)
    pSt.setInt(1,id)
    val res = pSt.executeQuery()
    //This is bad in case of incorrect ID
    val m = resultToMeasurement(res)(0)
    pSt.close()
    return m
  }

  def getAll(): List[Measurement] = {
    val query = "SELECT * FROM measurements;"
    val st = con.createStatement()
    val res = st.executeQuery(query)
    val ms = resultToMeasurement(res)
    st.close()
    return ms
  }
  def getLog(m: Measurement): List[MeasurementLog] = {
    val query = "SELECT * FROM measurement_logs WHERE mid=?;"
    val pSt = con.prepareStatement(query)
    pSt.setInt(1,m.id)
    val res = pSt.executeQuery()
    val ls = resultToMeasurementLog(res)
    pSt.close()
    return ls
  }
  def getAllLogs(): List[MeasurementLog] = {
    val query = "SELECT * FROM measurement_logs;"
    val pSt = con.prepareStatement(query)
    val res = pSt.executeQuery()
    val ls = resultToMeasurementLog(res)
    pSt.close()
    return ls
  }
  protected def newId(): Int = {
    val query = "SELECT MAX(id) FROM measurements;"
    val st = con.createStatement()
    val nId = st.executeQuery(query).getInt(1) + 1
    st.close()
    return nId
  }

  protected def newLogId(): Int = {
    val query = "SELECT MAX(id) FROM measurement_logs;"
    val st = con.createStatement()
    val nId = st.executeQuery(query).getInt(1) + 1
    st.close()
    return nId
  }

  def update(m: Measurement) = {
    val query = "UPDATE measurements SET name=?, value=? WHERE id=?;"
    val pSt = con.prepareStatement(query)
    pSt.setInt(3,m.id)
    pSt.setString(1,m.name)
    pSt.setInt(2,m.value)
    pSt.executeUpdate()
    pSt.close()
  }

  def updateLog(l: MeasurementLog) = {
    val query = "UPDATE measurement_logs SET mid=?, timestamp=?, message=? WHERE id=?;"
    val pSt = con.prepareStatement(query)
    pSt.setInt(4,l.id)
    pSt.setInt(1,l.mid)
    pSt.setInt(2,(l.date.getMillis()/1000).toInt)
    pSt.setString(3,l.message)
    pSt.executeUpdate()
    pSt.close()
  }

  def add(name: String) : Measurement = {
    val query = "INSERT INTO measurements (id,name,value) VALUES(?,?,?);"
    val pSt = con.prepareStatement(query)
    val ID = newId()
    pSt.setInt(1, ID)
    pSt.setString(2,name)
    pSt.setInt(3,0)
    pSt.executeUpdate()
    pSt.close()
    return getById(ID)
  }
  protected def addLog(m: Measurement, date: DateTime, message: String) = {
    //val l = new MeasurementLog(newLogId(),m.id, date, message)
    val query = "INSERT INTO measurement_logs (id,mid,timestamp,message) VALUES(?,?,?,?);"
    val pSt = con.prepareStatement(query)
    pSt.setInt(1,newLogId())
    pSt.setInt(2,m.id)
    pSt.setInt(3,(date.getMillis()/1000).toInt)
    pSt.setString(4,message)
    pSt.executeUpdate()
    pSt.close()
  }
  protected def resultToMeasurement(res: ResultSet): List[Measurement] = {
    var ms: List[Measurement] = List()
    while(res.next()) {
      ms ::= new Measurement(res.getInt("id"),res.getString("name"),res.getInt("value"))
    }
    return ms
  }
  protected def resultToMeasurementLog(res: ResultSet): List[MeasurementLog] = {
    var ls: List[MeasurementLog] = List()
    while(res.next()) {
      ls ::= new MeasurementLog(res.getInt("id"),res.getInt("mid"),new DateTime(res.getInt("timestamp").toLong * 1000),res.getString("message"))
    }
    return ls
  }

  def closeConnection(): Unit = {
    con.close()
  }

}

