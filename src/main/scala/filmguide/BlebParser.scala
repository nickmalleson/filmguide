package filmguide

import org.jsoup.Jsoup
import org.jsoup.nodes.{Document, Element}
import org.jsoup.select.Elements

import scala.collection.JavaConverters._
import scala.collection.mutable.ArrayBuffer

/**
  * Parser for data on http://www.bleb.org/
  *
  * Created by nick on 30/04/2017.
  */
class BlebParser extends ScheduleParser {
  /**
    * Get the schedule and parse it, returning an array of Program objects.
    *
    * Do this by going online to http://www.bleb.org/tv/all.html and using url parameters to access relevant pages
    */
  override def getProgrammes(): ArrayBuffer[Program]  = {

    // List of programs that is gradually populated
    var progs = ArrayBuffer[Program]()

    // First get today's schedule. The url is slightly different to the remaining days

    // URL for today
    var url = "http://www.bleb.org/tv/all.html?c="+BlebParser.CHANELS+"&all"
    println("Parsing url for today: "+url)

    // Get the schedule
    val doc = Jsoup.connect(url).get();

    // Add today's programs
    progs ++= BlebParser.parseSchedule(doc)

    // Now get the schedule for the remaining days

    // URL for remaining days (see '&day=X' at the end):
    //"http://www.bleb.org/tv/all.html?c="+BlepParser.CHANELS+"&all&day=1"

    progs
  }



}

object BlebParser {

  // The chanels to search.
  val CHANELS = "bbc1+bbc2+bbc_3+bbc4+film_four+ch4+five+e4"

  def apply() = new BlebParser()

  /**
    * Parse the html schedule for a particular day
    * @param doc The Jsoup Document
    * @return A list of Program objects
    */
  def parseSchedule(doc : Document): ArrayBuffer[Program] = {

    val progs = ArrayBuffer[Program]()

    // Get the div='content' section
    val content : Element = doc.getElementById("content");

    // There is one big table, then separate tables for each channel
    val bigTables = content.getElementsByClass("small-listings")
    assert(bigTables.size() == 1, "Was only expeting one table of class 'small-listings', not "+bigTables.size())
    val bigTable = bigTables.get(0)

    //println("*************************** BIG TABLE ***************************")
    //println(bigTable.toString)
    //println("*************************** END BIG TABLE ***************************")

    val smallTables = bigTable.getElementsByTag("table")

    println("Found "+smallTables.size()+" small tables")

    //println("*************************** SMALL TABLE 1 ***************************")
    //println(smallTables.get(0))
    //println(" *************************** END SMALL TABLE 1 ***************************")

    //println("*************************** SMALL TABLE 2 ***************************")
    //println(smallTables.get(1))
    //println(" *************************** END SMALL TABLE 2 ***************************")

    // Loop over each table and all then parse all rows.
    for ( (table, i) <- smallTables.iterator().asScala.zipWithIndex ) {

      if (i > 0) { // Ignore first table. For some reason this is the big table repeated

        //println(table)
        //println("****************************************")
        for (row <- table.getElementsByTag("tr").iterator().asScala) {
          //println("\tROW" + row)

          // See if this row contains a film, otherwise ignore it
          if (row.toString.contains("<i>Film</i>")) {
            // The row should contain two data items - the name of the film and the date
            val data: Elements = row.getElementsByTag("td")
            assert(data.size() == 2, "I was expecting this row to have two data elements, not " +
              data.size() + ":\n**ROW**:" + row + "\n**DATA**" + data)
            // The date is the first element
            val date = data.get(0).text()
            // The film is the second element (minus a few characters to get rid of the text 'film' that is
            // appended to the end of the film name.
            val name = data.get(1).text().substring(0, data.get(1).text().size-5)
            println("\t" + date + " ---- " + name)
          }
        }
      }
    }

    progs
  }

}