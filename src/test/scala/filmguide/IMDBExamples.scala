package filmguide

object IMDBExamples {

  private val dir = "resources/"

  val the_incredibles : String =
    scala.io.Source.fromFile(dir+"Example-the_incredibles.txt").mkString

  val hell_or_high_water : String =
    scala.io.Source.fromFile(dir+"Example-hell_or_high_water.txt").mkString

  val multiple_films : String =
    scala.io.Source.fromFile(dir+"Example-multiple_films.txt").mkString


}
