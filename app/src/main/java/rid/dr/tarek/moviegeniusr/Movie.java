package rid.dr.tarek.moviegeniusr;

/**
 * Created by Tarek on 11/28/2016.
 */

public class Movie {
    private String title;
    private String description;
    private String subInfo;
    private String rating;

    public Movie(String title, String description, String subInfo, String rating) {
        this.title = title;
        this.description = description;
        this.subInfo = subInfo;
        this.rating = rating;
    }

    public String getTitle() {
        return title;
    }
}
