package cmdui

import core._
import org.apache.commons.cli._
import java.io.File

/**
  * Created by DiscipleOfShinku on 11/5/2016.
  */

object CmdUI {

  def main(args: Array[String]): Unit = {

    var dbName = new String(System.getProperty("user.home") + "/.jpersonalstat/data.db")
    new File(dbName).getParentFile().mkdir()
    var db = SQLiteInitializer.init(dbName)
    var measurementInterface = new SQLiteMeasurementInterface(db)
    var options = new Options()

    //default doesn't work now
    var input = new Option("s", "show", false, "--show [default: --all] | [ -l id ] | [ -m id ]")
    input.setRequired(false)
    options.addOption(input)
    input = new Option("a", "all", false, "lists all measurements")
    input.setRequired(false)
    options.addOption(input)
    input = new Option("l", "log", true, "logs of measurement; ID is required")
    input.setRequired(false)
    options.addOption(input)
    input = new Option("m", "measurement", true, "shows measurement; ID is required")
    input.setRequired(false)
    options.addOption(input)
    input = new Option("c", "create", false, "--create [default: --new]")
    input.setRequired(false)
    options.addOption(input)
    input = new Option("n", "new", true, "creates new measurement; name is required")
    input.setRequired(false)
    options.addOption(input)
    input = new Option("x", "change", false, "--change [default: --increment]")
    input.setRequired(false)
    options.addOption(input)
    input = new Option("i", "increment", true, "increments measurement; ID is required")
    input.setRequired(false)
    options.addOption(input)
    /*
    what's required?
    input = new Option("L", "new_log", true, "create new measurement")
    input.setRequired(false)
    optionGroup.addOption(input)
    options.addOptionGroup(optionGroup)
    */

    var parser = new DefaultParser()
    var formatter = new HelpFormatter()

    try {
      var cmd: CommandLine = parser.parse(options, args)
      if (cmd.hasOption("s")){
        if (cmd.hasOption("a")) {
          var allMeasurements: String = measurementInterface.getAll().toString()
          System.out.println(allMeasurements)
        }
        else if (cmd.hasOption("l")) {
          var measurementLogs: String = measurementInterface.getLog(measurementInterface.getById(cmd.getOptionValue('l').toInt)).toString()
          System.out.println(measurementLogs)
        }
        else if (cmd.hasOption("m")) {
          var measurement: String = measurementInterface.getById(cmd.getOptionValue('m').toInt).toString()
          System.out.println(measurement)
        }
        else formatter.printHelp("jpersonalstat", options)
      }
      else if (cmd.hasOption("c")){
        if (cmd.hasOption("n")) {
          measurementInterface.add(cmd.getOptionValue('n'))
          // Is it even possible now?
          // measurementInterface.addLog(new Measurement(cmd.getOptionValue('n')), )
          System.out.println("You have created measurement")
        }
        else formatter.printHelp("jpersonalstat", options)
      }
      else if (cmd.hasOption("x")) {
        if (cmd.hasOption("i")) {
          //Does not work now
          measurementInterface.update(measurementInterface.getById(cmd.getOptionValue('i').toInt)).toString()
          //measurementInterface.updateLog(measurementInterface.getLog(measurementInterface.getById(cmd.getOptionValue('i').toInt)).last)
          System.out.println("You have incremented measurement")
        }
        else formatter.printHelp("jpersonalstat", options)
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
