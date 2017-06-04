package filmguide

import scala.io.Source
import scala.util.matching.Regex

/**
  * Created by nick on 26/05/2017.
  */
object IMDBRater extends RatingLookup {

  val RATINGS_FILE = "./resources/ratings.list" // A list of all IMDB films and ratings. See the project README for info.
  val RATINGS_DICT = _readRatings(RATINGS_FILE )

  /**
    * Look up the given program for its rating on RottenTomatoes.
    * See: https://www.apitools.com/apis/rottentomatoes
    */
  override def lookup(prog: Program): Rating = {

    // TODO Implement this

    return Rating(4.3f, 10, prog)
  }


  /**
    * Read the ratings file. See the project README for info about where this comes from.
    *
    * Each line in the file that contains a raiting will look like this:
    *
    *  0000002310  115731   7.7  Hell or High Water (2016/II)
    *
    * The first part is the distribution of the votes, the next is the number of votes, then the overall vote,
    * finally the film name.
    *
    * @param filename
    * @return A map of titles to their ratings
    */
  def _readRatings(filename:String): Map[String,Float] = {

    // A pattern to match the relevant lines with named groups
    val pat = new Regex("""\s+..........\s+(\d+)\s+(\d\.\d)\s+(.+)""", "num_votes", "rating", "title")

    // Store a tuple of (num votes, rating) for each film.
    val ratingsDict = scala.collection.mutable.HashMap[String,(Int,Float)]()

    try {
      for (line <- (Source.fromFile(RATINGS_FILE)(io.Codec("latin1"))).getLines()) {

        //println(line)

        // Look for a line that is in the right format. The findFirstMatchIn returns an Option
        // (Some if a match was found (in which case refer to the result object as 'r'), None otherwise)
        val result = pat.findFirstMatchIn(line)
        result match {

          case None => _ // No match - not a film rating line

          case Some(r) => { // Have matched the line, so this is a film
            //println(line+"\n\t"+r.group("num_votes") + ", " + r.group("rating")+" , "+r.group("title"))
            val votes  = r.group("num_votes").toInt
            val rating = r.group("rating").toFloat
            val title  = r.group("title")
            if (ratingsDict.contains(title)) {
              // TODO check to see if the map already contains the film. If so, accept a replacement entry only if it has more votes
            }
            else {
              ratingsDict += (title -> (votes,rating))

            }

          } // case

        } // match

      } // for lines
    } catch {
      case ex: Exception => println("Got an exception when reading the file:", ex.toString)

    }

    print(ratingsDict)

    // TODO return the ratingsDict in the required format (String->Float) (can forget about number of votes)

    return Map("Title"->5f)

  }

  def main( args:Array[String] ):Unit = {

  }

  }
