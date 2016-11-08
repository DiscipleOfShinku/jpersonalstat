package cmdui

import core._
import org.apache.commons.cli._

/**
  * Created by DiscipleOfShinku on 11/5/2016.
  */

object CmdUI {

  def main(args: Array[String]): Unit = {

    var dbName = new String("./initialBase")
    var db = SQLiteInitializer.init(dbName)
    var measurementInterface = new SQLiteMeasurementInterface(db)
    var options = new Options()

    var input = new Option("a", "all", false, "lists all measurements")
    input.setRequired(false)
    options.addOption(input)
    input = new Option("l", "log", true, "logs of measurement; ID is required")
    input.setRequired(false)
    options.addOption(input)
    input = new Option("s", "show", true, "shows measurement; ID is required")
    input.setRequired(false)
    options.addOption(input)
    input = new Option("n", "new", true, "creates new measurement; name is required")
    input.setRequired(false)
    options.addOption(input)
    input = new Option("i", "increment", true, "increments measurement; ID is required")
    input.setRequired(false)
    options.addOption(input)
    /*
    what's required?
    input = new Option("L", "new_log", true, "create new measurement")
    input.setRequired(false)
    options.addOption(input)
    */

    var parser = new DefaultParser()
    var formatter = new HelpFormatter()

    try {
      var cmd: CommandLine = parser.parse(options, args)
      if (cmd.hasOption("a")) {
        var allMeasurements: String = measurementInterface.getAll().toString()
        System.out.println(allMeasurements)
      }
      if (cmd.hasOption("l")) {
        var measurementLogs: String = measurementInterface.getLog(measurementInterface.getById(cmd.getOptionValue('l').toInt)).toString()
        System.out.println(measurementLogs)
      }
      if (cmd.hasOption("s")) {
        var measurement: String = measurementInterface.getById(cmd.getOptionValue('s').toInt).toString()
        System.out.println(measurement)
      }
      if (cmd.hasOption("n")) {
        measurementInterface.add(cmd.getOptionValue('n'))
        // Is it even possible now?
        // measurementInterface.addLog(new Measurement(cmd.getOptionValue('n')), )
        System.out.println("You have created measurement")
      }
      if (cmd.hasOption("i")) {
        //Does not work now
        measurementInterface.update(measurementInterface.getById(cmd.getOptionValue('i').toInt)).toString()
        //measurementInterface.updateLog(measurementInterface.getLog(measurementInterface.getById(cmd.getOptionValue('i').toInt)).last)
        System.out.println("You have incremented measurement")
      }

    } catch {
      case e: ParseException =>
        System.out.println(e.getMessage())
        formatter.printHelp("jpersonalstat", options)

      System.exit(1)
      return
    }
  }
}
