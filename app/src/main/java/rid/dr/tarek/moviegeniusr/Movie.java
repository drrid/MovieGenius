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

    public Movie(String b_URL, String title, String description, String subInfo,
                 String rating, String year, String posterURL, boolean hasPoster,
                 String pathToPoster, String imdbID, String duration,
                 String pathToBackground, boolean hasBackground, String torrentURL) {
        this.b_URL = b_URL;
        this.title = title;
        this.description = description;
        this.subInfo = subInfo;
        this.rating = rating;
        this.year = year;
        this.posterURL = posterURL;
        this.hasPoster = hasPoster;
        this.pathToPoster = pathToPoster;
        this.imdbID = imdbID;
        this.duration = duration;
        this.pathToBackground = pathToBackground;
        this.hasBackground = hasBackground;
        this.torrentURL = torrentURL;
    }

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

        if (hasPoster != movie.hasPoster) return false;
        if (hasBackground != movie.hasBackground) return false;
        if (b_URL != null ? !b_URL.equals(movie.b_URL) : movie.b_URL != null) return false;
        if (!title.equals(movie.title)) return false;
        if (description != null ? !description.equals(movie.description) : movie.description != null)
            return false;
        if (subInfo != null ? !subInfo.equals(movie.subInfo) : movie.subInfo != null) return false;
        if (rating != null ? !rating.equals(movie.rating) : movie.rating != null) return false;
        if (!year.equals(movie.year)) return false;
        if (posterURL != null ? !posterURL.equals(movie.posterURL) : movie.posterURL != null)
            return false;
        if (pathToPoster != null ? !pathToPoster.equals(movie.pathToPoster) : movie.pathToPoster != null)
            return false;
        if (imdbID != null ? !imdbID.equals(movie.imdbID) : movie.imdbID != null) return false;
        if (duration != null ? !duration.equals(movie.duration) : movie.duration != null)
            return false;
        if (pathToBackground != null ? !pathToBackground.equals(movie.pathToBackground) : movie.pathToBackground != null)
            return false;
        if (torrentURL != null ? !torrentURL.equals(movie.torrentURL) : movie.torrentURL != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = b_URL != null ? b_URL.hashCode() : 0;
        result = 31 * result + title.hashCode();
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (subInfo != null ? subInfo.hashCode() : 0);
        result = 31 * result + (rating != null ? rating.hashCode() : 0);
        result = 31 * result + year.hashCode();
        result = 31 * result + (posterURL != null ? posterURL.hashCode() : 0);
        result = 31 * result + (hasPoster ? 1 : 0);
        result = 31 * result + (pathToPoster != null ? pathToPoster.hashCode() : 0);
        result = 31 * result + (imdbID != null ? imdbID.hashCode() : 0);
        result = 31 * result + (duration != null ? duration.hashCode() : 0);
        result = 31 * result + (pathToBackground != null ? pathToBackground.hashCode() : 0);
        result = 31 * result + (hasBackground ? 1 : 0);
        result = 31 * result + (torrentURL != null ? torrentURL.hashCode() : 0);
        return result;
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
