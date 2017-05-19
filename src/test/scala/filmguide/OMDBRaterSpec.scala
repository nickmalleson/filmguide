package filmguide

/**
  * Created by nick on 19/05/2017.
  */
class OMDBRaterSpec extends UnitSpec {

  "The  parsarseIMDBText function" should "be able to find the IMDB rating for films" in {

    // Example of text that would be returned from the API
    val info = OMDBExampleJson.x

    val (filmName, rating, outof) = OMDBRater.parseIMDBText(info)

    print(filmName + " " + rating + " " + outof)

    // XXXX HERE - now test this

    )

  }

}
