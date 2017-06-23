package filmguide


import java.io.PrintWriter

import scala.collection.mutable.ArrayBuffer


/**
  * Created by nick on 30/04/2017.
  */
object Main {

  /**
    * Application entry point
    * @param args
    */
  def main( args:Array[String] ):Unit = {

    val programmes = BlebParser.getProgrammes()

    println("Have found all the films, now checking these against IMDB ratings")

    val ratings = ArrayBuffer[Rating]()
    var failures = 0 // Save the number of films that couldn't be matched. The titles are printed by the lookup() function
    for (prog <- programmes) {
      IMDBRater.lookup(prog) match {
        case None => failures += 1
        case Some(rating) => ratings += rating // Found the film, add its rating to the list
      }
    }

    println("Finished. There were "+failures+" films that could not be processed.")

    // Sort the ratings
    val ratings_sort = ratings.sortWith(_.rating > _.rating)

    // Print and write out results

    println("The films, ordered by rating, are:")
    var s = ""
    for (r <- ratings_sort) {
      s += r.p.name+","+r.rating+"\n"
    }
    println(s)

    val name = "filmratings.txt"
    val outfile = new PrintWriter(name)
    outfile.print(s)
    outfile.close()

    println("Have written these to: "+name)

  } // main

}




