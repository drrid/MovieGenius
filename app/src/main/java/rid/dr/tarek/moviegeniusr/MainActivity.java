package rid.dr.tarek.moviegeniusr;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {
    RecyclerView moviesList;
    MovieAdapter movieAdapter;
    List<Movie> myList = new ArrayList<Movie>();
    MoviePresenter moviePresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if(moviePresenter == null){
            moviePresenter = new MoviePresenter();
        }

        //TODO: cach movies so when I enter the app I see the last downloaded info


        //TODO: refresh recyclerView items according to movies stream
        // init RecyclerView
        moviesList = (RecyclerView)findViewById(R.id.rv_movies);
        movieAdapter = new MovieAdapter(myList, getApplication());

        moviesList.setHasFixedSize(true);
        moviesList.setLayoutManager(new LinearLayoutManager(this));
        moviesList.setAdapter(movieAdapter);
    }

    public void onBtnClick(View view) {
        Observable.fromCallable(()->moviePresenter.getLatestMovies())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(movies -> updateList(movies),
                        throwable -> errBtn(throwable));
    }

    private void errBtn(Throwable throwable) {
        System.out.println(throwable.getMessage());
    }

    private void updateList(List<Movie> movies) {
        myList.clear();
        myList.addAll(movies);
        movieAdapter.notifyDataSetChanged();
    }


    private void testData() {
        myList.add(new Movie("Batman" , "The dark knight returns", null, null));
        myList.add(new Movie("Batman2", "The dark knight returns", null, null));
        myList.add(new Movie("Batman3", "The dark knight returns", null, null));
        myList.add(new Movie("Batman4", "The dark knight returns", null, null));
        myList.add(new Movie("Batman5", "The dark knight returns", null, null));
        myList.add(new Movie("Batman6", "The dark knight returns", null, null));
        myList.add(new Movie("Batman7", "The dark knight returns", null, null));
        myList.add(new Movie("Batman8", "The dark knight returns", null, null));
    }

}
