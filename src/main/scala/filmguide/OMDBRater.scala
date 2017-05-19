package filmguide

import scala.util.matching.Regex

/**
  * Object that queries the OpenMovieDatabase API for information about
  * a film.
  *
  * See: https://www.omdbapi.com/
  *
  * This returns ratings from a number of agencies including
  * IMDB and Rotten Tomatoes
  *
  * Created by nick on 19/05/2017.
  */
object OMDBRater extends RatingLookup {

  /**
    * The agency to get the ratings from. These are a bit like an enum.
    * Could add others
    */
  val agency : AGENCY = imdb

  /**
    * Look up the given program for its rating
    */

  override def lookup(prog: Program): Rating = {


    return Rating(4.3f, 5, "XX")
  }



  /**
    * Parse the text from the OMDB and get the IMDB ratings.
    * @param text
    * @return A tuple with the name of the film, its rating, and the score it is out of
    */
  def parseIMDBText(text: String) : (String, Float, Int) = {
    return ("film name", 4.5f, 5)
  }


}

/* The agency to return results from, the total that the score is out of,
  * and a function that will parse its text.
  * The possibilities are:
  * "Ratings":[{"Source":"Internet Movie Database","Value":"7.8/10"},{"Source":"Rotten Tomatoes","Value":"75%"},{"Source":"Metacritic","Value":"68/100"}]
  */
case class AGENCY(name:String, outOf: Int, f:String => (String, Float, Int))
case object imdb extends AGENCY("Internet Movie Database", 10, OMDBRater.parseIMDBText)
// Could add the other agencies here
