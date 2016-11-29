package rid.dr.tarek.moviegeniusr;

import org.json.JSONException;
import org.json.JSONObject;
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

    private final static String OMDB_BASEURL = "http://www.omdbapi.com/?t=%s&y=%s&plot=short&r=json";

    public List<Movie> getLatestMovies() throws IOException {

        String title_fin;
        String year;
        String f_year;
        List<String> titles = new ArrayList<String>();
        List<Movie> movies = new ArrayList<Movie>();
        List<String> years = new ArrayList<String>();

        Document movies_doc = Jsoup.connect("https://www.shaanig.org/f30/").get();
        Elements titles_els = movies_doc.getElementsByClass("threadtitle");

        // Convert Elements to ArrayList
        for(Element text_el: titles_els){
            if(text_el.text().contains("1080p")){
                // String manipulation and cleaning
                year = text_el.text().split("\\(", 2)[1];
                year = year.split("\\)",2)[0];
                title_fin = text_el.text().split("\\(", 2)[0];
                title_fin = title_fin.replace("Sticky:", "").trim();

                //populating titles and links to ArrayList
                titles.add(title_fin);
                years.add(year);
            }
        }

        for(String title:titles){
            f_year = years.get(titles.indexOf(title));
            movies.add(new Movie(title,null,null,null,f_year));
        }
        return movies;
    }


    public Movie getInfo(Movie movie){

        Movie n_movie = movie;
        String q_title = n_movie.getTitle().replace(" ", "+");
        String year = n_movie.getYear();

        JSONObject json = null;
        try {
            String doc = Jsoup.connect(String.format(OMDB_BASEURL, q_title, year))
                    .ignoreContentType(true).get().text();
            json = new JSONObject(doc);

            // get info
            String description = json.getString("Plot");
            String rating = json.getString("imdbRating");

            // set info
            n_movie.setDescription(description);
            n_movie.setRating(rating);

        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return  n_movie;
    }

}
