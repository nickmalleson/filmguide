package filmguide

import java.io.PrintWriter

import scala.collection.mutable
import scala.io.Source
import scala.util.matching.Regex

/**
  * Created by nick on 26/05/2017.
  */
object IMDBRater extends RatingLookup {


  // When this class is initialised read the ratings file and store it in a dictionary
  val RATINGS_FILE = "./resources/ratings.list" // A list of all IMDB films and ratings. See the project README for info.
  val RATINGS_DICT = _readRatings(RATINGS_FILE )

  /**
    * Look up the given program for its rating on RottenTomatoes.
    * See: https://www.apitools.com/apis/rottentomatoes
    */
  override def lookup(prog: Program): Option[Rating] = {

    // Get rid of the 'Film' text, year, and anything after it
    val title = prog.name.split("""Film \(\d\d\d\d""")(0).trim.replace("\"","")

    if (RATINGS_DICT.contains(title) ) {
      return Some(Rating(RATINGS_DICT(title),10,prog) )
    }
    else {
      println("Could not find a program called "+prog)
      return None
    }

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

    println("IMDBRater is reading the list of all films ... ")

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

          case None => () // No match - not a film rating line, return a Unit

          case Some(r) => { // Have matched the line, so this is a film
            //println(line+"\n\t"+r.group("num_votes") + ", " + r.group("rating")+" , "+r.group("title"))

            val votes  = r.group("num_votes").toInt
            val rating = r.group("rating").toFloat
            val full_title  = r.group("title").trim

            // Get rid of the year, and anything after it
            //println()
            //println(full_title)
            //println(full_title.split("""\(\d\d\d\d\)""").mkString("--"))
            val title = full_title.split("""\(\d\d\d\d""")(0).trim.replace("\"","")

            //println("Title:"+title+ " votes:"+votes + ", Rating:" + rating)
            if (ratingsDict.contains(title)) {
              // The dict already contains the film.  Accept  this as replacement entry only if it has more votes
              val (v, r) = ratingsDict(title)
              if (v > votes) {
                //println("\tReplacing film", v, votes, title, rating)
                ratingsDict += (title -> (votes,rating))
              }
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

    //print(ratingsDict)

    println("\t ... finished reading IMDB file.")

    // Return the ratingsDict in the required format (String->Float) (can forget about number of votes)

    return {
      for ( (title,x) <- ratingsDict ) yield {
          title -> x._2 // Return the title and the rating (second part of the (votes,rating) tuple
        } // yield
     }.toMap[String, Float]



  } // _readBuildings

  def main( args:Array[String] ):Unit = {

    // Write the ratings dictionary to a file to test
    new PrintWriter("temp.txt") { write(
      RATINGS_DICT.mkString("\n")
    ); close }

  }

} // IMDBRater object
