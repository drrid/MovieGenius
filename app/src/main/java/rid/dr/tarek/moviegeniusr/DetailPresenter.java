package rid.dr.tarek.moviegeniusr;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.File;

/**
 * Created by Tarek on 11/30/2016.
 */

public class DetailPresenter {

    public Bitmap setPosterImg(Movie movie) {
        File file = new File(movie.getPathToPoster());
        Bitmap img = BitmapFactory.decodeFile(file.getPath());
        return img;
    }
}
