package filmguide

import scala.collection.mutable.ArrayBuffer

object Main {


    val programmes = BlebParser.getProgrammes()

    val ratings = ArrayBuffer[Rating]()
    var failures = 0 // Save the number of films that couldn't be matched. The titles are printed by the lookup() function
    for (prog <- programmes) {
      IMDBRater.lookup(prog) match {
        case None => failures += 1
        case Some(rating) => ratings += rating // Found the film, add its rating to the list
      }
    }

    print("Finished. There were"+failures+" films that could not be processed.")

    // Sort the ratings
    val ratings_sort = ratings.sortWith(_.rating < _.rating)

    print("The films, ordered by rating, are:")
    for (r <- ratings_sort) {
      print(r.p.name+","+r.rating)
    }

}




