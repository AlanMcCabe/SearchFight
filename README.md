# SearchFight

SearchFight allows you to rate the popularity of different terms online by searching for queries and totalling
the number of results from various different search engines.

## Use

The program can be executed on command line by running the supplied jar file directly with the search terms
to be compared supplied as arguments. For example, to compare the search terms "search" and "fight":

	java -jar SearchFight.jar search fight

Terms comprised of multiple words can be searched by providing them in quotation marks:

	java -jar SearchFight.jar "search fight"

Alternatively, if any changes have been made to the program or configuration it can be compiled by navigating
to the root directory (containing pom.xml) and executing:

	mvn clean package

This assumes that you have Maven installed. The updated jar can then be ran by executing:

	java -jar target/SearchFight-1.0-jar-with-dependencies.jar ${search terms}

## Extending

Additional search engines can be queried by adding them to the searchEngines.json file. The following information
must be provided:

	"name": Name of the search engine

	"searchUrl": The search engine URL including the search path e.g
			"http://www.google.com/search?q="
		     It should be possible to query the search engine by appending text to this URL.

	"selector": The Jsoup selector syntax used to select the "About x results" element of the page.
                    The element can be found by selecting the text, right click and selecting e.g "View Selection Source"
                    (on Firefox).
		    The Jsoup selector syntax can be found at https://jsoup.org/cookbook/extracting-data/selector-syntax

Please note that malformed JSON or incorrect searchUrl/selectors will prevent the program from running as intended.
