import java.util.*;

public class WebCrawler {
    // Maximum pages to visit to search the word
    private final int MAX_PAGES_TO_SEARCH = 10;

    // Stores unique web pages (URLs)
    private Set<String> pageVisited = new HashSet<String>();

    // List of URLs to visit
    private Queue<String> pagesToVisit = new LinkedList<>();

    private int numOfPageVisits;

    public void searchForWord(String URL, String word){
        // Add the main URL to visit
        pagesToVisit.add(URL);
        String currentURL;
        Crawler crawler = new Crawler();

        while(this.pageVisited.size() < MAX_PAGES_TO_SEARCH){

            ++numOfPageVisits;

            currentURL = this.nextURLToVisit();
            crawler.crawl(currentURL);

            if(crawler.search(word)){
                System.out.println("*** Found word: " + word + " @ " + currentURL);
                break;
            }

            pagesToVisit.addAll(crawler.getURLs());
        }

        System.out.println("Pages visited: " + numOfPageVisits);
    }

    // Helper method to avoid visiting same URLs
    private String nextURLToVisit(){
        String URL = null;

        if(!this.pagesToVisit.isEmpty()) {
            URL = this.pagesToVisit.remove();
            while (pageVisited.contains(URL)) {
                URL = this.pagesToVisit.remove();
            }

            // Add to visited list
            pageVisited.add(URL);
        }
        return URL;
    }

}
