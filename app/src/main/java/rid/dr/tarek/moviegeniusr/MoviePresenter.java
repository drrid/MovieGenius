package rid.dr.tarek.moviegeniusr;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tarek on 11/29/2016.
 */

public class MoviePresenter {

    private static final String TAG = "DRRID";
    private File path;
    private final static String OMDB_BASEURL = "http://www.omdbapi.com/?t=%s&y=%s&plot=short&r=json";
    private final static String BASEURL = "https://www.google.com/search?q=";

    public void setPath(File path) {
        this.path = path;
    }

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
            if(!movies.contains(new Movie(title, null, null, null, null))){
                movies.add(new Movie(title,null,null,null,f_year));
            }
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
            String poster_url = json.getString("Poster");
            String imdbID = json.getString("imdbID");
            String duration = json.getString("Runtime");

            // set info
            n_movie.setPosterURL(poster_url);
            n_movie.setDescription(description);
            n_movie.setRating(rating);
            n_movie.setImdbID(imdbID);
            n_movie.setDuration(duration);

        } catch (IOException e) {
            Log.d(TAG, "getInfo: "+ e.getMessage());
        } catch (JSONException e) {
            Log.d(TAG, "getInfo: "+ e.getMessage());
        }
        return  n_movie;
    }

    public Movie getPoster(Movie movie) {

        Movie n_movie = movie;
        File file_check = new File(path, "/" + movie.getTitle()+".jpg");

        if(file_check.exists()){
            String pathToPoster = file_check.getPath();
            n_movie.setPathToPoster(pathToPoster);
            n_movie.setHasPoster(true);
        }
        else{
            try {
                URL url = new URL(movie.getPosterURL());
                InputStream in = new BufferedInputStream(url.openStream());
                ByteArrayOutputStream out = new ByteArrayOutputStream();
                byte[] buf = new byte[1024];
                int n = 0;

                while (-1!=(n=in.read(buf))){
                    out.write(buf, 0, n);
                }

                out.close();
                in.close();

                byte[] response = out.toByteArray();
                File file = new File(path, "/" + movie.getTitle()+".jpg");
                FileOutputStream fos = new FileOutputStream(file);
                fos.write(response);
                fos.close();

                String pathToPoster = file.getPath();
                n_movie.setPathToPoster(pathToPoster);
                n_movie.setHasPoster(true);


            } catch (IOException e) {
                Log.d(TAG, "getPoster: "+ e.getMessage());
            }
        }
        return n_movie;
    }

    public Movie getBackground(Movie movie) {
        Movie n_movie = movie;
        String q_title = n_movie.getTitle().replace(" ", "+");
        File file_check = new File(path, "/" + movie.getTitle() + "-bg" + ".jpg");
        if(file_check.exists()){
            String pathToBackground = file_check.getPath();
            n_movie.setPathToBackground(pathToBackground);
            n_movie.setHasBackground(true);
        }
        else{
            try {
                Document g_doc = Jsoup.connect(BASEURL+q_title+"+fanart.tv").get();
                Element g_el = g_doc.getElementsByClass("r").first();
                String fanart_url = g_el.getElementsByAttribute("href").attr("href");

                Document doc = Jsoup.connect(fanart_url).get();
                Element el = doc.getElementsByClass("artwork moviethumb").first();
                String bgImgURL = el.getElementsByAttribute("href").attr("href");
                bgImgURL = "https://fanart.tv"+bgImgURL;

                URL url = new URL(bgImgURL);
                InputStream in = new BufferedInputStream(url.openStream());
                ByteArrayOutputStream out = new ByteArrayOutputStream();
                byte[] buf = new byte[1024];
                int n = 0;

                while (-1!=(n=in.read(buf))){
                    out.write(buf, 0, n);
                }

                out.close();
                in.close();

                byte[] response = out.toByteArray();
                File file = new File(path, "/" + movie.getTitle() + "-bg" + ".jpg");
                FileOutputStream fos = new FileOutputStream(file);
                fos.write(response);
                fos.close();

                String pathToBackground = file.getPath();
                n_movie.setPathToBackground(pathToBackground);
                n_movie.setHasBackground(true);

            } catch (IOException e) {
                Log.d(TAG, "getBackground: "+ e.getMessage());
            }
        }
        return n_movie;
    }
}
