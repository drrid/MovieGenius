package rid.dr.tarek.moviegeniusr;

import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ProgressBar;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    private Database db = new Database(this);
    private RecyclerView moviesListRV;
    private MovieAdapter movieAdapter;
    private List<Movie> myList = new ArrayList<Movie>();
    private MoviePresenter moviePresenter;
    private ProgressBar pb;
    private File path;
    private static final String TAG = "DRRID";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //create movie presenter
        if (moviePresenter == null) {
            moviePresenter = new MoviePresenter();
        }

        //init imgs path and pass it to Presenter
        path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MOVIES);
        moviePresenter.setPath(path);

        // init RecyclerView
        moviesListRV = (RecyclerView) findViewById(R.id.rv_movies);
        movieAdapter = new MovieAdapter(myList, getApplication(), path);
        moviesListRV.setHasFixedSize(true);
        moviesListRV.setLayoutManager(new LinearLayoutManager(this));
        moviesListRV.setAdapter(movieAdapter);
        DividerItemDecoration dividerItemDecoration =
                new DividerItemDecoration(moviesListRV.getContext(), 1);
        moviesListRV.addItemDecoration(dividerItemDecoration);

        //ProgressBar
        pb = (ProgressBar) findViewById(R.id.progressBar);

        //Network data request
        Observable<Movie> netObs = moviePresenter.getLMObs()
                .doOnNext(mvs -> pb.setMax(mvs.size()))
                .flatMap(mvs -> Observable.from(mvs))
                .filter(movie -> (myList.contains(movie) == false))
                .flatMap(mv -> moviePresenter.getInfoObs(mv))
                .retry(10)
                .delay(500, TimeUnit.MILLISECONDS)
                .doOnCompleted(() -> cacheMovies());

        //Database data request
        Observable<Movie> dbObs = db.loadMoviesObs()
                .filter(movies -> (movies!=null))
                .flatMap(mvs -> Observable.from(mvs));

        //data sources combined
        Observable.concat(dbObs, netObs)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnEach((mv) -> pb.incrementProgressBy(1))
                .subscribe(
                        mv -> updateItem(mv),
                        throwable -> throwable.printStackTrace());
    }

    private void updateItem(Movie movie) {
        int i = myList.indexOf(movie);
        myList.remove(movie);
        if (myList.contains(movie)) {
            myList.add(i, movie);
        } else {
            myList.add(movie);
        }
        movieAdapter.notifyDataSetChanged();
    }

    private void cacheMovies() {
        List<Movie> movies = myList;
        Observable.fromCallable(() -> db.saveMovies(movies))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(d -> Log.d(TAG, "cacheMovies: " + d),
                        throwable -> throwable.printStackTrace());
    }
}
