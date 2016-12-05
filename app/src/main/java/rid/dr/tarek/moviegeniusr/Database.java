package rid.dr.tarek.moviegeniusr;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Tarek on 12/5/2016.
 */

public class Database extends SQLiteOpenHelper{

    public Database(Context context) {
        super(context, "movies.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE movies(imdbID TEXT PRIMARY KEY, title TEXT, year INTEGER," +
                "info TEXT, rating INTEGER, duration TEXT, hasPoster BOOLEAN, hasBG BOOLEAN)";

        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
