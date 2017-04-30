package filmguide

import scala.collection.mutable.ArrayBuffer

/**
  * Parser for data on http://www.bleb.org/
  *
  * Created by nick on 30/04/2017.
  */
class BlepParser extends ScheduleParser {
  /**
    * Get the schedule data that needs to be parsed. Return an array where each element is the schedule for a
    * day. The first element is the current day, reamining elements are for subsequent days.
    *
    * Do this by going online to http://www.bleb.org/tv/all.html and using url parameters to access relevant pages
    */
  override def getSchedule(): ArrayBuffer[String] = {

    val schedule = ArrayBuffer[String]()

    // First get today's schedule

    // URL for today
    val urlToday = "http://www.bleb.org/tv/all.html?c="+BlepParser.CHANELS+"&all"
    print("Parsing url for today: "+urlToday)


    // URL for remaining days (see '&day=X' at the end):
    "http://www.bleb.org/tv/all.html?c="+BlepParser.CHANELS+"&all&day=1"

  }

  /**
    * Parse the schedule
    */
  override def parseSchedule(schedule: String): ArrayBuffer[String] = ???
}

object BlepParser {

  // The chanels to search.
  val CHANELS = "bbc1+bbc2+bbc_3+bbc4+film_four+ch4+five+e4"

}