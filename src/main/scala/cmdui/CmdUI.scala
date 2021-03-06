package cmdui

import core._
import org.apache.commons.cli._
import java.io.File
import com.github.nscala_time.time.Imports._

/**
  * Created by DiscipleOfShinku on 11/5/2016.
  */

//That is for testing
class CmdUI {
}

//That is for running application
object CmdUI extends CmdUI {

  def main(args: Array[String]): Unit = {

    val dbName = new String(System.getProperty("user.home") + "/.jpersonalstat/data.db")
    new File(dbName).getParentFile().mkdir()
    val db = SQLiteInitializer.init(dbName)
    val measurementInterface = new SQLiteMeasurementInterface(db)
    val options = new Options()

    var input = new Option("h", "help", false, "show this help")
    input.setRequired(false)
    options.addOption(input)
    input = new Option("s", "show", false, "[--show | -s] [--all | -A] [-m | -l] [<ID>]")
    input.setRequired(false)
    options.addOption(input)
    input = new Option("A", "all", false, "list all measurements or logs")
    input.setRequired(false)
    options.addOption(input)
    input = new Option("m", true, "show measurement; ID is required")
    input.setRequired(false)
    options.addOption(input)
    //problem: with argument and without argument are required
    input = new Option("l", true, "logs of measurement; ID is required")
    input.setRequired(false)
    options.addOption(input)
    input = new Option("a", "add", true, "create new measurement; name is required")
    input.setRequired(false)
    options.addOption(input)
    input = new Option("i", "inc", true, "increment measurement; ID is required")
    input.setRequired(false)
    options.addOption(input)

    /* does not implemented yet
    input = new Option("c", "change", false, "--change [default: --increment]")
    input.setRequired(false)
    options.addOption(input)
    */

    val parser = new DefaultParser()
    val formatter = new HelpFormatter()

    try {
      val cmd: CommandLine = parser.parse(options, args)
      if (cmd.hasOption("h")){
        formatter.printHelp("jpersonalstat", options)
      }
      else if (cmd.hasOption("s")){
        if (cmd.hasOption("A")) {
          if (cmd.hasOption("l")) {
            val allLogs: String = measurementInterface.getAllLogs().toString()
            System.out.println(allLogs)
          }
          else {
            val allMeasurements: String = measurementInterface.getAll().toString()
            System.out.println(allMeasurements)
          }
        }
        else if (cmd.hasOption("m")) {
          val measurement: String = measurementInterface.getById(cmd.getOptionValue('m').toInt).toString()
          System.out.println(measurement)
        }
        else if (cmd.hasOption("l")) {
          val measurementLogs: String = measurementInterface.getLog(measurementInterface.getById(cmd.getOptionValue('l').toInt)).toString()
          System.out.println(measurementLogs)
        }
        else {
          val allMeasurements: String = measurementInterface.getAll().toString()
          System.out.println(allMeasurements)
        }
      }
      else if (cmd.hasOption("a")){
        val ID = measurementInterface.add(cmd.getOptionValue('a')).id
        System.out.println("You have created measurement with ID: " + ID)
      }
      else if (cmd.hasOption("i")) {
        val dateTime = new DateTime()
        measurementInterface.inc(measurementInterface.getById(cmd.getOptionValue('i').toInt), "Incrementing measurement", DateTime.now)
        System.out.println("You have incremented measurement.")
      }
      else if (cmd.hasOption("c")) {
        //This command does not implemented yet.
        System.out.println("This command does not implemented yet.")
      }
      else formatter.printHelp("jpersonalstat", options)
    } catch {
      case e: ParseException =>
        System.out.println(e.getMessage())
        formatter.printHelp("jpersonalstat", options)

        System.exit(1)
        return
    }
  }
}