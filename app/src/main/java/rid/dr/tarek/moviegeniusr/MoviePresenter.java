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

    final static String BASEURL = "http://www.google.com/search?q=";

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
            movies.add(new Movie(title,null,null,null,null));
        }
        return movies;
    }


    public Movie getInfo(Movie movie){

        Movie n_movie = movie;
        String q_title = n_movie.getTitle().replace(" ", "+");

        // get imdb link
        Document doc = null;
        try {
            doc = Jsoup.connect(BASEURL+q_title+"+IMDB").get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Element result = doc.getElementsByClass("r").first();
        String imdb_link = result.getElementsByAttribute("href").attr("href");


        Document des_doc = null;
        try {
            des_doc = Jsoup.connect(imdb_link).get();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // get description
        String description = des_doc.getElementsByClass("summary_text").text();
        n_movie.setDescription(description);

        // get rating
        String rating = des_doc.getElementsByClass("ratingValue").text();
        n_movie.setRating(rating);

        // get year
        String year = des_doc.getElementById("titleYear").text();
        n_movie.setYear(year);

        // get subInfo
        String subInfo = des_doc
                .getElementsByClass("subtext")
                .text()
                .split("\\)")[0]
                .concat(")");
        n_movie.setSubInfo(subInfo);

        return  n_movie;
    }

}
