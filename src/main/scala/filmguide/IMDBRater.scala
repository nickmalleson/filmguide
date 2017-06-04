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




    try {
      for (line <- (Source.fromFile(RATINGS_FILE)(io.Codec("latin1"))).getLines()) {

        println(line)

        // Look for a line that is in the right format
        for (matchString <- pat.findAllIn(line)) {
          // XXXX HERE WORK OUT HOW TO ACCESS THE GROUPS
          println("\t",matchString.toString)
          println("\t",matchString.group("num_votes", "rating", "title")))
        }




      }
    } catch {
      case ex: Exception => println("Got an exception when reading the file:", ex.toString)
    }


    return Map("Title"->5f)

  }

  def main( args:Array[String] ):Unit = {

  }

  }
