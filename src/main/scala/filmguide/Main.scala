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
      ratings += IMDBRater.lookup(prog)
    }

  }

}




