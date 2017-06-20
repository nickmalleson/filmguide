package filmguide

/**
  * Created by nick on 19/05/2017.
  */
trait RatingLookup {
  /**
    * Look up the given program for its rating (returning None if it can't be found)
    */
  def lookup(prog:Program): Option[Rating]

}
