package rid.dr.tarek.moviegeniusr;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.List;

public class Database extends SQLiteOpenHelper{

    private static final String MOVIES_TABLE = "movies";
    private static final String COL_ID = "imdbID";
    private static final String COL_TITLE = "title";
    private static final String COL_YEAR = "year";
    private static final String COL_INFO = "info";
    private static final String COL_RATING = "rating";
    private static final String COL_DURATION = "duration";
    private static final String COL_H_POSTER = "hasPoster";
    private static final String COL_H_BG = "hasBG";

    public Database(Context context) {
        super(context, "movies.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = String.format("CREATE TABLE %s (%s TEXT PRIMARY KEY" +
                    ", %s TEXT, %s INTEGER," +
                    "%s TEXT, %s INTEGER, %s TEXT, " +
                    "%s BOOLEAN, %s BOOLEAN)", MOVIES_TABLE, COL_ID, COL_TITLE
                    , COL_YEAR, COL_INFO, COL_RATING, COL_DURATION, COL_H_POSTER, COL_H_BG);

        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public String saveMovies(List<Movie> movies){
        SQLiteDatabase db = getWritableDatabase();
        db.delete(MOVIES_TABLE, null, null);

        for(Movie movie:movies){
            ContentValues cv = new ContentValues();
            cv.put(COL_ID, movie.getImdbID());
            cv.put(COL_TITLE, movie.getTitle());
            cv.put(COL_YEAR, movie.getYear());
            cv.put(COL_INFO, movie.getDescription());
            cv.put(COL_RATING, movie.getRating());
            cv.put(COL_DURATION, movie.getDuration());
            cv.put(COL_H_POSTER, movie.isHasPoster());
            cv.put(COL_H_BG, movie.isHasBackground());

            db.insert(MOVIES_TABLE, null, cv);
        }
        db.close();
        return "done";
    }

    public List<Movie> loadMovies(){
        SQLiteDatabase db = getReadableDatabase();
        String sql = String.format("SELECT * FROM %s", MOVIES_TABLE);


        db.close();
        return null;
    }
}
