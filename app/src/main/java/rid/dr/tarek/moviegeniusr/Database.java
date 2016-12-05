package rid.dr.tarek.moviegeniusr;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

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

    private static final String COL_B_URL = "bURL";
    private static final String COL_SUBINFO = "subinfo";
    private static final String COL_POSTERURL = "posterURL";
    private static final String COL_PATHTOPOSTER = "pToPoster";
    private static final String COL_PATHTOBACKGROUND = "pToBG";
    private static final String COL_TORRENTURL = "tURL";
    List<Movie> movies2 = new ArrayList<Movie>();

    public Database(Context context) {
        super(context, "movies3.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = String.format("CREATE TABLE %s (%s TEXT PRIMARY KEY" +
                    ", %s TEXT, %s TEXT," +
                    "%s TEXT, %s TEXT, %s TEXT, " +
                    "%s BOOLEAN, %s BOOLEAN, %s TEXT, %s TEXT," +
                    " %s TEXT, %s TEXT, %s TEXT, %s TEXT)", MOVIES_TABLE, COL_ID, COL_TITLE
                    , COL_YEAR, COL_INFO, COL_RATING, COL_DURATION, COL_H_POSTER, COL_H_BG,
                    COL_B_URL, COL_SUBINFO, COL_POSTERURL, COL_PATHTOPOSTER,
                    COL_PATHTOBACKGROUND, COL_TORRENTURL);

        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    public String saveMovies(List<Movie> movies){
        movies2.addAll(movies);
        SQLiteDatabase db = getWritableDatabase();
        db.delete(MOVIES_TABLE, null, null);

        for(Movie movie:movies2){
            ContentValues cv = new ContentValues();
            cv.put(COL_ID, movie.getImdbID());
            cv.put(COL_TITLE, movie.getTitle());
            cv.put(COL_YEAR, movie.getYear());
            cv.put(COL_INFO, movie.getDescription());
            cv.put(COL_RATING, movie.getRating());
            cv.put(COL_DURATION, movie.getDuration());
            cv.put(COL_H_POSTER, movie.isHasPoster());
            cv.put(COL_H_BG, movie.isHasBackground());

            cv.put(COL_B_URL, movie.getBaseURL());
            cv.put(COL_SUBINFO, movie.getSubInfo());
            cv.put(COL_POSTERURL, movie.getPosterURL());
            cv.put(COL_PATHTOPOSTER, movie.getPathToPoster());
            cv.put(COL_PATHTOBACKGROUND, movie.getPathToBackground());
            cv.put(COL_TORRENTURL, movie.getTorrentURL());

            db.insert(MOVIES_TABLE, null, cv);
        }
        db.close();
        return "done";
    }

    public List<Movie> loadMovies(){
        List<Movie> movies = new ArrayList<Movie>();
        SQLiteDatabase db = getReadableDatabase();
        String sql = String.format("SELECT * FROM %s", MOVIES_TABLE);
        Cursor cursor = db.rawQuery(sql, null);

        while(cursor.moveToNext()){
            String imdbID = cursor.getString(0);
            String title = cursor.getString(1);
            String year = cursor.getString(2);
            String description = cursor.getString(3);
            String rating = cursor.getString(4);
            String duration = cursor.getString(5);
            boolean hPoster = cursor.getInt(6)!=0;
            boolean hBG = cursor.getInt(7)!=0;

            String bURL = cursor.getString(8);
            String subinfo = cursor.getString(9);
            String posterURL = cursor.getString(10);
            String pathToPoster = cursor.getString(11);
            String pathToBG = cursor.getString(12);
            String torrentURL = cursor.getString(13);

            Movie movie = new Movie(bURL, title, description, subinfo, rating, year,
                    posterURL, hPoster, pathToPoster, imdbID, duration, pathToBG, hBG, torrentURL);

            movies.add(movie);
        }

        db.close();
        return movies;
    }

    public Observable<List<Movie>> loadMoviesObs(){
        return Observable.defer(()->Observable.just(loadMovies()))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}