package rid.dr.tarek.moviegeniusr;

import java.io.File;
import java.io.Serializable;

/**
 * Created by Tarek on 11/28/2016.
 */

public class Movie implements Serializable{
    private String title;
    private String description;
    private String subInfo;
    private String rating;
    private String year;
    private String posterURL;
    private boolean hasPoster = false;
    private String pathToPoster;
    private String imdbID;
    private String duration;
    private String pathToBackground;
    private boolean hasBackground = false;

    public Movie(String title, String description, String subInfo, String rating, String year) {
        this.title = title;
        this.description = description;
        this.subInfo = subInfo;
        this.rating = rating;
        this.year = year;
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
}
