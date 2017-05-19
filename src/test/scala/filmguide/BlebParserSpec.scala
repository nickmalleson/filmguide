package filmguide

import org.jsoup.Jsoup
import org.scalatest.GivenWhenThen

import scala.collection.mutable.ArrayBuffer

/**
  * Created by nick on 05/05/2017.
  */
class BlebParserSpec extends UnitSpec {

  "The match function" should "be able to match films" in {

    val films = Array(
      "A League of Their Own</a> <i>Film (1992)",   // Film
      "The House of Magic</a> <i>Film (2013)</i>",  // Film
      "Clash of the Titans</a> <i>Film</i>",         // Film
      "The Staircase Implementation</a></i>",       // Not film
      "Film Four" // Not film
    )
    val isFilm = Array(true, true, true, false, false)

    (films, isFilm).zipped.foreach( (text, isAFilm) => {
        Given(text)
        val m = BlebParser.matches(text)
        Then( if (isAFilm) "A match should be found" else  "No match should be found")
        assert(m == isAFilm)
      }
    )

  }

  "The parseSchedule function" should "return 10 programmes using example data" in {

    val doc = Jsoup.parse(BlebExampleHTML.complete_html) // Create a document from the test data
    val progs = BlebParser.parseSchedule(doc)
    println("Found the following programs:\n"+ (for (prog <- progs) yield prog+"\n").toString() )
    assert(progs.size == 17)

  }

  it should "throw an UnparseableException if it cannot parse the HTML" in {

    Given("Unexpected html")
    val doc = Jsoup.parse("XYZ") // Create a document from the test data

    Then("An Unparseable Exceptionshould be thrown")
    an [UnparseableException] should be thrownBy {
      val progs = BlebParser.parseSchedule(doc)
    }
  }

  it should "throw an UnparseableException if it cannot find any programs" in {
    Given("Incomplete html")
    val doc = Jsoup.parse(BlebExampleHTML.incomplete_html) // Create a document from the test data

    Then("An Unparseable Exceptionshould be thrown")
    an [UnparseableException] should be thrownBy {
      val progs = BlebParser.parseSchedule(doc)
    }
  }

  /*
  it should "throw an UnparseableException if it cannot find any programmes in the example data" in {

    val doc = Jsoup.parse(BlebExampleHTML.x) // Create a document from the test data


    val progs = BlebParser.parseSchedule(doc)
    assert(progs.size == 10)
    UnparseableException
  }

*/


}


/**
  * Created by nick on 07/05/2016.
class ActivitySpec extends UnitSpec with GivenWhenThen with Matchers {

  // Tests consist of a *subject*, a *verb* (either 'should', 'must', or 'can') and the rest of the sentence

  "The  checkTimes function" should "throw an illegal argument exception if the input arry size is 0" in {
    an [IllegalArgumentException] should be thrownBy {
      TimeProfile.checkTimes(Array.empty[(Double,Double)])
    }
  }


  it should "throw an illegal argument exception if any of the times are not in the range [0-24)" in {
    val i = 1.0 // the intensity
    val t = 1.0 // the time

    Given("A profile with a time of 24.0")
    val profile1: Array[(Double,Double)] = Array( (t,i) , (24.0, i) , (t,i), (t,i)  )

    Then("An Illegal argument should be thrown")
    an [IllegalArgumentException] should be thrownBy {
      TimeProfile.checkTimes(profile1)
    }

    And("")
    Given("A profile with a time of 24.01")
    val profile2: Array[(Double,Double)] = Array( (t,i) , (24.01,i) , (t,i), (t,i)  )

    Then("An Illegal argument should be thrown")
    an [IllegalArgumentException] should be thrownBy {
      TimeProfile.checkTimes(profile2)
    }

    And("")
    Given("A profile with a time of -0.01")
    val profile3: Array[(Double,Double)] = Array( (t,i) , (-0.01,i) , (t,i), (t,i)  )

    Then("An Illegal argument should be thrown")
    an [IllegalArgumentException] should be thrownBy {
      TimeProfile.checkTimes(profile3)
    }

  }

  it should "throw an illegal argument exception if the input times are not in ascending order" in {

    val i = 1.0 // the intensity

    val p = Array( (0d,i) , (10d, i) , (9d,i), (15d,i)  )
    an [IllegalArgumentException] should be thrownBy {
      TimeProfile.checkTimes(p)
    }
  }


  it should "return Unit if the times are in ascending order and in the range [0-24)" in {

    val i = 1.0 // the intensity

    val p2 = Array( (0d,i) , (10d, i) , (13d,i), (15d,i)  )
    TimeProfile.checkTimes(p2)
  }


  "The calcIntensity() function" should "throw an error if the input time is not in the range [0-24]" in {
    val tp = TimeProfile(Array((0d, 0d), (10d, 8.0), (13d, 14.0), (15d, 10.0), (23d, 5.0)))
    Given("An imput less than 0")
    Then("An illegal argument should be thrown")
    an [IllegalArgumentException] should be thrownBy {
      tp.calcIntensity(-0.01)
    }
    And("")
    Given("An input of 24")
    an [IllegalArgumentException] should be thrownBy {
      tp.calcIntensity(24.0)
    }
  }

  it should "return a single intensity regardless of the input time parameter if the TimeProfile only has one data point " in {
    val INTENSITY = 15d
    val tp = TimeProfile(Array((5d, INTENSITY)) ) // An Array with only 1 (time,intensity) Pair

    for (t <- Seq(1d, 15d, 23d)) {
      tp.calcIntensity(t) should equal (INTENSITY)
    }

  }

  it should "return the exact intensity if the passed time period exists" in {
    val tp = TimeProfile(Array((0d, 0d), (10d, 8.0), (13.5d, 14.0), (15d, 10.0), (23d, 5.0)))
    tp.calcIntensity(10) should equal (8)
    tp.calcIntensity(13.5) should equal (14)
    tp.calcIntensity(15) should equal (10)

  }

  it should "interpolate correctly with constant intensities" in {

    val tp = TimeProfile(Array( (10d, 5d), (13d, 5d), (15d, 5d) ) )
    for (t <- Seq(1d, 10d, 10.5 , 13d, 23d, 23.5)) {
      tp.calcIntensity(t) should equal (5d)
    }

  }

  it should "interpolate correctly with varied intensities" in {

    val tp = TimeProfile(Array((5d, 0d), (10d, 8.0), (13d, 14.0), (15d, 10.0), (23d, 5.0)))
    // print out the intensities, for checking
    //for (t <- Seq(1d, 10d, 10.5, 13d, 17d, 20d, 23.5)) {
    // val i = tp.calcIntensity(t)
    //  println(s"XX t:$t  -  i:$i")
    //}

    tp.calcIntensity(1d) should be > 0d
    tp.calcIntensity(1d) should be < 5d
    //tp.calcIntensity(1d) should equal (XX)

    tp.calcIntensity(10d) should equal (8d)

    tp.calcIntensity(10.5) should be > 8d
    tp.calcIntensity(10.5) should be < 14d
    tp.calcIntensity(10.5) should equal (9d)

    tp.calcIntensity(13d) should equal (14d)

    tp.calcIntensity(17d) should be < 10d
    tp.calcIntensity(17d) should be > 5d

    tp.calcIntensity(20d) should be < 10d
    tp.calcIntensity(20d) should be > 5d
    tp.calcIntensity(20d) should equal (6.875)

    tp.calcIntensity(23.5) should be < 5d
    tp.calcIntensity(23.5) should be > 0d

  }


}
*/