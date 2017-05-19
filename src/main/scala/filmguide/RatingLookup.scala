package filmguide

/**
  * Created by nick on 19/05/2017.
  */
trait RatingLookup {
  /**
    * Look up the given program for its rating
    */
  def lookup(prog:Program): Rating

}
