package filmguide

/**
  * Created by nick on 30/04/2017.
  */
object Main {

  /**
    * Application entry point
    * @param args
    */
  def main( args:Array[String] ):Unit = {

    val parser = BlebParser()
    val programmes = parser.getProgrammes()


  }

}
