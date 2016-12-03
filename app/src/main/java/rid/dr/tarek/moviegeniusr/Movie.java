package rid.dr.tarek.moviegeniusr;


import java.io.Serializable;


/**
 * Created by Tarek on 11/28/2016.
 */

public class Movie implements Serializable{
    private String b_URL = null;
    private String title = null;
    private String description = null;
    private String subInfo = null;
    private String rating = null;
    private String year = null;
    private String posterURL = null;
    private boolean hasPoster = false;
    private String pathToPoster = null;
    private String imdbID = null;
    private String duration = null;
    private String pathToBackground = null;
    private boolean hasBackground = false;
    private String torrentURL = null;

    public Movie(String title, String year, String b_URL) {
        this.title = title;
        this.year = year;
        this.b_URL = b_URL;
    }

    public String getRating() {
        return rating;
    }

    public String getPathToPoster() {
        return pathToPoster;
    }

    public void setPathToPoster(String pathToPoster) {
        this.pathToPoster = pathToPoster;
    }

    public String getTitle() {
        return title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public void setSubInfo(String subInfo) {
        this.subInfo = subInfo;
    }

    public String getYear() {
        return year;
    }

    public String getSubInfo() {
        return subInfo;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Movie movie = (Movie) o;

        if (!title.equals(movie.title)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return title.hashCode();
    }

    public void setPosterURL(String posterURL) {
        this.posterURL = posterURL;
    }

    public String getPosterURL() {
        return posterURL;
    }

    public boolean isHasPoster() {
        return hasPoster;
    }

    public void setHasPoster(boolean hasPoster) {
        this.hasPoster = hasPoster;
    }

    public void setImdbID(String imdbID) {
        this.imdbID = imdbID;
    }

    public String getImdbID() {
        return imdbID;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getDuration() {
        return duration;
    }

    public void setPathToBackground(String pathToBackground) {
        this.pathToBackground = pathToBackground;
    }

    public String getPathToBackground() {
        return pathToBackground;
    }

    public void setHasBackground(boolean hasBackground) {
        this.hasBackground = hasBackground;
    }

    public boolean isHasBackground() {
        return hasBackground;
    }

    public String getBaseURL() {
        return b_URL;
    }

    public void setTorrentURL(String torrentURL) {
        this.torrentURL = torrentURL;
    }

    public String getTorrentURL() {
        return torrentURL;
    }
}
