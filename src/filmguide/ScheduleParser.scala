package filmguide

import scala.collection.mutable.ArrayBuffer

/**
  * Super class for the code to parse the TV listing information
  * Created by nick on 30/04/2017.
  */
trait ScheduleParser {

  /**
    * Get the schedule data that needs to be parsed. Return an array where each element is the schedule for a
    * day. The first element is the current day, reamining elements are for subsequent days.
    */
  def getSchedule() : ArrayBuffer[String] = {}

  /**
    * Parse the schedule
    */
  def parseSchedule( schedule: String) : ArrayBuffer[Program]






}
