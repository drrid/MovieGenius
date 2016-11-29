package rid.dr.tarek.moviegeniusr;

/**
 * Created by Tarek on 11/28/2016.
 */

public class Movie {
    private String title;
    private String description;
    private String subInfo;
    private String rating;
    private String year;

    public Movie(String title, String description, String subInfo, String rating, String year) {
        this.title = title;
        this.description = description;
        this.subInfo = subInfo;
        this.rating = rating;
        this.year = year;
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

    public String getYear() {
        return year;
    }

    public String getSubInfo() {
        return subInfo;
    }

    public String getDescription() {
        return description;
    }
}
