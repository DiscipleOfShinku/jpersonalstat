package cmdui

import core._
import org.apache.commons.cli._

/**
  * Created by DiscipleOfShinku on 11/5/2016.
  */

object CmdUI {

  def main(args: Array[String]): Unit = {

    var options = new Options()

    var input = new Option("id", true, "ID of measurement")
    input.setRequired(false)
    options.addOption(input)
    input = new Option("a", "all", false, "list all measurements")
    input.setRequired(false)
    options.addOption(input)

    var parser = new DefaultParser()
    var formatter = new HelpFormatter()

    try {
      var cmd: CommandLine = parser.parse(options, args)
      var measurementID: String = cmd.getOptionValue("id")
      System.out.println(measurementID)
      /* if cmd.hasOption("a") {
        var allMeasurements: String = getAll()
        System.out.println(allMeasurements)
      } */
    } catch {
      case e: ParseException =>
        System.out.println(e.getMessage())
        formatter.printHelp("jpersonalstat", options)

      System.exit(1)
      return
    }
  }
}
