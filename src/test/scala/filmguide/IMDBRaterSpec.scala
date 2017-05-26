package filmguide

import org.jsoup.Jsoup

/**
  * Created by nick on 19/05/2017.
  */
class IMDBRaterSpec extends UnitSpec {

  "The _parseRating function" should "be able to find the IMDB rating from html for films" in {

    // Example of text that would be returned from the API

    val hell = IMDBExamples.hell_or_high_water

    val incredibles = IMDBExamples.the_incredibles

    assert (IMDBRater._parseRating("Hell or High Water") == 7.7f)

    assert (IMDBRater._parseRating("The Incredibles") == 8f)

  }

  it should "throw an UnparseableException if it cannot parse the HTML" in {

    Given("Html that doesn't describe a single film")
    val multiple = IMDBExamples.multiple_films // Wont get a result from this one

    Then("An Unparseable Exceptionshould be thrown")
    an [UnparseableException] should be thrownBy {
      IMDBRater._parseRating(multiple)
    }

  }

  "The _getFilmHTML function" should "run without errors" in {

    IMDBRater._getFilmHTML(Program("Hell or High Water"))

    IMDBRater._getFilmHTML(Program("The Incredibles"))


  }



}
