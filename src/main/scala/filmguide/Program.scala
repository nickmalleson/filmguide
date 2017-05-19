package filmguide

/**
  * Store information about a TV programme or film
  * Created by nick on 30/04/2017.
  *
  * @constructor Creates a new programme with a name and scheduled date
  * @param name The name of the programme
  */
case class Program ( name: String ) {


  override def toString: String = name

}
