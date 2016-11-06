package core

import java.sql.Connection
import java.sql.DriverManager
/**
  * Created by nekomiko on 04.11.16.
  */
object SQLiteInitializer {
  def createTableIfNotExists(c: Connection, tableName: String, schema: String): Unit = {
    val query = "SELECT COUNT(*) FROM sqlite_master WHERE type='table' AND name=?;"
    val tSt = c.prepareStatement(query)
    tSt.setString(1,tableName)
    val rs = tSt.executeQuery()

    if (rs.getInt(1) == 0){
      val cSt = c.createStatement()
      println("Table " + tableName + " not exists, creating new")
      cSt.executeUpdate("CREATE TABLE IF NOT EXISTS " + tableName + " " + schema + ";")
      cSt.close()
    }
    else
      //println("Table " + tableName +" exists, OK")
    tSt.close()
  }

  def init(dbpath: String): Connection = {
    Class.forName("org.sqlite.JDBC")
    val c: Connection = DriverManager.getConnection("jdbc:sqlite:" ++ dbpath)
    createTableIfNotExists(c,"measurements", "(id INTEGER, name TEXT,value INTEGER)")
    createTableIfNotExists(c,"measurement_logs", "(id INTEGER, mid INTEGER, timestamp INTEGER, message TEXT)")
    return c
  }
}
