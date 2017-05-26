package filmguide

/**
  * Created by nick on 26/05/2017.
  */
object IMDBRater extends RatingLookup {

  /**
    * Look up the given program for its rating on RottenTomatoes.
    * See: https://www.apitools.com/apis/rottentomatoes
    */
  override def lookup(prog: Program): Rating = {

    // TODO Implement this

    return Rating(4.3f, 10, prog)
  }

  /**
    * Parse a rating from some IMDB html
    * @param s The html that contains the rating (somewhere)
    */
  def _parseRating(s:String): Float  = {
    // TODO Implement this
    return 4.3f
    // throw UnparseableException("Could not find a film rating in input string")
  }

  /**
    * Given a program, go to IMDB and search for it's rating.
    * @param prog
    * @return The HTML string returned from the program search.
    */
  def _getFilmHTML(prog: Program): String = {
    // TODO Implement this

    throw UnparseableException("Could not get the web page for the film: "+prog)
  }
}
