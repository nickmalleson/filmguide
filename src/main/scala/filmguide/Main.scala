package filmguide

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

    val ratings = ArrayBuffer[Rating]()
    for (prog <- programmes) {
      IMDBRater.lookup(prog) match {
        case None => // No match, do nothing
        case Some(rating) => ratings += rating // Found the film, add its rating to the list
        //case None => println("Could not match film: "+prog.name)

      }
    }

    // TODO Display films ordered by rating
    println(ratings.mkString("\n"))

  } // main

}




