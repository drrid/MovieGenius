package rid.dr.tarek.moviegeniusr;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tarek on 11/29/2016.
 */

public class MoviePresenter {

    public List<Movie> getLatestMovies() throws IOException {

        String title_fin;
        List<String> titles = new ArrayList<String>();
        List<Movie> movies = new ArrayList<Movie>();

        Document movies_doc = Jsoup.connect("https://www.shaanig.org/f30/").get();
        Elements titles_els = movies_doc.getElementsByClass("threadtitle");

        // Convert Elements to ArrayList
        for(Element text_el: titles_els){
            if(text_el.text().contains("1080p")){
                // String manipulation and cleaning
                title_fin = text_el.text().split("\\(", 2)[0];
                title_fin = title_fin.replace("Sticky:", "").trim();

                //populating titles and links to ArrayList
                titles.add(title_fin);
            }
        }

        for(String title:titles){
            movies.add(new Movie(title,null,null,null));
        }
        return movies;
    }


}
