package filmguide

/**
  * Super class for the code to parse the TV listing information
  * Created by nick on 30/04/2017.
  */
trait ScheduleParser {

  /**
    * Get the schedule data that needs to be parsed
    */
  def getSchedule() : String

  /**
    * Parse the schedule
    */
  def parseSchedule( schedule: String) : Array[Programe]






}
