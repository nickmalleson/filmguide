# FilmGuide

Code to go online, find all films that are showing on (some) freeview channels this week, find their IMDB ratings, and print a list of films ordered by rating.

This makes it easy if you want to set your recorder to record _good_ films over the next week :-)

I use the great website [www.bleb.org/tv/](http://www.bleb.org/tv/) to find the program schedules. Thank you to whoever has made that site, this wouldn't be possible without it.

Also thanks to IMDB as they very helpfully publish a text version of their ratings, see: [http://www.imdb.com/interfaces](http://www.imdb.com/interfaces)

## Usage Instructions

To make the code work, you will need to download the [list of ratings](ftp://ftp.fu-berlin.de/pub/misc/movies/database/ratings.list.gz) file and extract it into the [resources](./resources) folder. It needs to be called `ratings.list`. I haven't included it in the project as IMDB might want the traffic.

The code is in the [Scala](http://www.scala-lang.org/) language. I used [IntelliJ IDEA](https://www.jetbrains.com/idea/) to write the code, so the easiest way to run the program is to download IDEA and open this whole project in there. Then run the file ``src/main/scala/filmguide/Main.scala``