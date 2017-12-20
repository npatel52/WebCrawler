import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.LinkedList;

/**
 * Basic web-crawler that searches for a word
 * on a given web-page and on links from the main web-page.
 * It parses the web-page, stores URLs from that page, and
 * searches for the word.
 *
 * Author: Nimit Patel
 *
 * Source: http://www.netinstructions.com/
 */
public class Crawler {

    private LinkedList<String> URLs = new LinkedList<>();
    private Document htmlDocument;
    private String currentURL;

    // Fake User
    private static final String USER_AGENT =
            "Mozilla/5.0 (Windows NT 6.1; WOW64)";


    // Access web-page and extract URLs
    public boolean crawl(String URL){
        currentURL = URL;
        try{
            Connection connection = Jsoup.connect(URL).userAgent(USER_AGENT);
            htmlDocument = connection.get();

            // Verifies if the connection is established
            if(connection.response().statusCode() == 200){
                System.out.println("Fetched web page from URL: " + URL);
            }

            // Check for HTML format and text
            if(!connection.response().contentType().contains("text/html")){
                return false;
            }


            // Looks for links on pages and add it to the list
            Elements links = htmlDocument.select("a[href]");

            System.out.println("Found " + links.size() + " links on " + URL );

            for(Element link: links){
                this.URLs.add(link.absUrl("href"));
            }

        }catch (IOException ioe){
            System.out.println("Error in HTTP request " + ioe.getMessage());
            return false;
        }

        return true;
    }

    public boolean search(String word){
        if(htmlDocument == null)
            return false;

        System.out.println("Searching for word " + word + " on " + currentURL );
        return this.htmlDocument.body().text().toLowerCase().contains(word.toLowerCase());
    }

    public LinkedList<String> getURLs(){
        return this.URLs;
    }
}
