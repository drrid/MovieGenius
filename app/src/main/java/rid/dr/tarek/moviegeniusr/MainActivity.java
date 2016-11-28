package rid.dr.tarek.moviegeniusr;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    RecyclerView moviesList;
    MovieAdapter movieAdapter;
    List<Movie> myList = new ArrayList<Movie>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Test Data
        myList.add(new Movie("Batman", "The dark knight returns", null, null));
        myList.add(new Movie("Batman2", "The dark knight returns", null, null));
        myList.add(new Movie("Batman3", "The dark knight returns", null, null));
        myList.add(new Movie("Batman4", "The dark knight returns", null, null));
        myList.add(new Movie("Batman5", "The dark knight returns", null, null));
        myList.add(new Movie("Batman6", "The dark knight returns", null, null));
        myList.add(new Movie("Batman7", "The dark knight returns", null, null));
        myList.add(new Movie("Batman8", "The dark knight returns", null, null));

        // init RecyclerView
        moviesList = (RecyclerView)findViewById(R.id.rv_movies);
        movieAdapter = new MovieAdapter(myList, getApplication());

        moviesList.setHasFixedSize(true);
        moviesList.setLayoutManager(new LinearLayoutManager(this));
        moviesList.setAdapter(movieAdapter);

    }
}
