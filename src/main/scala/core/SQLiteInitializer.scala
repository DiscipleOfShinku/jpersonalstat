package core

import java.sql.Connection
import java.sql.DriverManager
/**
  * Created by nekomiko on 04.11.16.
  */
object SQLiteInitializer {
  def createTableIfNotExists(c: Connection, tableName: String, schema: String): Unit = {
    val cSt = c.createStatement()
    cSt.executeUpdate("CREATE TABLE IF NOT EXISTS " + tableName + " " + schema + ";")
    cSt.close()
  }

  def init(dbpath: String): Connection = {
    Class.forName("org.sqlite.JDBC")
    val c: Connection = DriverManager.getConnection("jdbc:sqlite:" ++ dbpath)
    createTableIfNotExists(c,"measurements", "(id INTEGER, name TEXT,value INTEGER)")
    createTableIfNotExists(c,"measurement_logs", "(id INTEGER, mid INTEGER, timestamp INTEGER, message TEXT)")
    return c
  }
}
