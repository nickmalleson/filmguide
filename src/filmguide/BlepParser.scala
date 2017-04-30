package filmguide

/**
  * Parser for data on http://www.bleb.org/
  *
  * Created by nick on 30/04/2017.
  */
class BlepParser extends ScheduleParser {
  /**
    * Get the schedule data that needs to be parsed
    */
  override def getSchedule(): String = ???

  /**
    * Parse the schedule
    */
  override def parseSchedule(schedule: String): Array[Any] = ???
}
