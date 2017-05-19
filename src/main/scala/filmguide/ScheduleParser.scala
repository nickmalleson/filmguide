package filmguide


/**
  * Super class for the code to parse the TV listing information
  * Created by nick on 30/04/2017.
  */
trait ScheduleParser {

  /**
    * Get the schedule and parse it, returning an array of Program objects.
    */
  def getProgrammes(): Iterable[Program]



}