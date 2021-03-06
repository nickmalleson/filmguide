package filmguide

import org.jsoup.Jsoup
import org.jsoup.nodes.{Document, Element}
import org.jsoup.select.Elements

import scala.collection.JavaConverters._
import scala.collection.mutable.Set
import scala.util.matching.Regex

/**
  * Parser for data on http://www.bleb.org/
  *
  * Created by nick on 30/04/2017.
  */
object BlebParser extends ScheduleParser {

  // The chanels to search.
  val CHANELS = "bbc1+bbc2+bbc4+film_four+ch4+five+e4+itv+itv3+itv4"

  /**
    * Get the schedule and parse it, returning an array of Program objects.
    *
    * Do this by going online to http://www.bleb.org/tv/all.html and using url parameters to access relevant pages
    */
  override def getProgrammes(): Iterable[Program]  = {

    // List of programs that is gradually populated
    var progs = Set[Program]()

    // Go through all days, each one has a different url (happily 0 is today)
    //for (day <- 0)
    for (day <- 0.until(6) )
    {
      var url = "http://www.bleb.org/tv/all.html?c="+BlebParser.CHANELS+"&all&day="+day
      println("Parsing url for day"+day+": "+url)

      // Get the schedule
      val doc = Jsoup.connect(url).get();

      // Add today's programs
      progs ++= BlebParser._parseSchedule(doc)

      Thread.sleep(1000) // Wait a second before getting the next page
    } // for each day

    // URL for remaining days (see '&day=X' at the end):
    //"http://www.bleb.org/tv/all.html?c="+BlepParser.CHANELS+"&all&day=1"

    progs
  }




  /**
    * Parse the html schedule for a particular day
    * @param doc The Jsoup Document
    * @return A list of Program objects
    * @throws UnparseableException if it cannot parse the html
    */
  def _parseSchedule(doc : Document): Iterable[Program] = {

    val progs = Set[Program]()

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
          if (BlebParser._matches(row.toString)) {
              // The row should contain two data items - the name of the film and the date
              val data: Elements = row.getElementsByTag("td")
              if (data.size() != 2) {
                val msg = "I was expecting this row to have two data elements, not " +
                  data.size() + ":\n**ROW**:" + row + "\n**DATA**" + data
                //print(msg)
                throw UnparseableException(msg)
              }
              // The date is the first element
              val date = data.get(0).text()
              // The film is the second element (minus a few characters to get rid of the text 'film' that is
              // appended to the end of the film name.
              //val name = data.get(1).text().substring(0, data.get(1).text().size-5)
              val name = data.get(1).text()
              println("\t" + date + " ---- " + name)
              progs += new Program(name)
            }
          }
        }
      } // for all rows

    progs
  }

  /**
    * See if this row is a flim. E.g. something like XXXX
    * @param row
    * @return
    */
  def _matches(row:String): Boolean = {

//    val pat = "<i>Film(x)?</i>".r
    return row.contains("Film</i") || row.contains("Film (")
  }

}