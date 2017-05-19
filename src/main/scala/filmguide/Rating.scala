package filmguide

/**
  * A rating given by e.g. IMDB, Rotten Tomatoes, etc
  * Created by nick on 19/05/2017.
  *
  * @param rating The rating itself (e.g. 4.5)
  * @param outOf The maximum rating a program could get
  */
case class Rating (val rating:Float, val outOf:Int ){



}
