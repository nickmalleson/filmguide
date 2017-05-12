package filmguide

import org.jsoup.Jsoup
import org.jsoup.nodes.{Document, Element}
import org.jsoup.select.Elements

import scala.collection.JavaConverters._
import scala.collection.mutable.ArrayBuffer
import scala.util.matching.Regex

/**
  * Parser for data on http://www.bleb.org/
  *
  * Created by nick on 30/04/2017.
  */
object BlebParser extends ScheduleParser {

  // The chanels to search.
  val CHANELS = "bbc1+bbc2+bbc_3+bbc4+film_four+ch4+five+e4"

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




  /**
    * Parse the html schedule for a particular day
    * @param doc The Jsoup Document
    * @return A list of Program objects
    * @throws UnparseableException if it cannot parse the html
    * X
    */
  def parseSchedule(doc : Document): ArrayBuffer[Program] = {

    val progs = ArrayBuffer[Program]()

    // Get the div='content' section
    val content : Element = doc.getElementById("content");

    if (content==null) {
      throw new UnparseableException("Could not find the 'content' element in the html.")
    }

    // There is one big table, then separate tables for each channel
    val bigTables = content.getElementsByClass("small-listings")

    if (bigTables.size != 1) {
      throw new UnparseableException("Was only expeting one table of class 'small-listings', not " + bigTables.size())
    }
    val bigTable = bigTables.get(0)

    //println("*************************** BIG TABLE ***************************")
    //println(bigTable.toString)
    //println("*************************** END BIG TABLE ***************************")

    val smallTables = bigTable.getElementsByTag("table")

    // If there are fewer than 2 entries then it couldn't find any programs (see next comment for why this is fewer
    // than 2 rather than 1
    if (smallTables.size <= 1) {
      throw new UnparseableException("Did not get any programs in the html")

    }

    // println("Found "+smallTables.size()+" small tables")

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
          val pat = "<i>Film(x)?</i>".r
          if (row.toString.contains(pat)) {
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
              progs += new Program(name, date)
            }
          }
        }
      }


    progs
  }

}