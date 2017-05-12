package filmguide

/**
  * Created by nick on 30/04/2017.
  */
object Main {

  /**
    * Application entry point
    * @param args
    */
  def main( args:Array[String] ):Unit = {

    val parser = BlebParser()
    val programmes = parser.getProgrammes()


  }

}

/**
  * Store information about a TV programme or film
  * Created by nick on 30/04/2017.
  *
  * @constructor Creates a new programme with a name and scheduled date
  * @param name The name of the programme
  * @param scheduled The date that the programme is scheduled to take place
  */
case class Program ( name: String, scheduled:String) {


}

import scala.collection.mutable.ArrayBuffer

/**
  * Super class for the code to parse the TV listing information
  * Created by nick on 30/04/2017.
  */
trait ScheduleParser {

  /**
    * Get the schedule and parse it, returning an array of Program objects.
    */
  def getProgrammes(): ArrayBuffer[Program]



}
