package rid.dr.tarek.moviegeniusr;

import android.app.ProgressDialog;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;

import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

public class MainActivity extends AppCompatActivity {

    private Database db = new Database(this);
    private RecyclerView moviesListRV;
    private MovieAdapter movieAdapter;
    private List<Movie> myList = new ArrayList<Movie>();
    private MoviePresenter moviePresenter;
    private ProgressDialog pd;
    private File path;
    private CompositeSubscription ss = new CompositeSubscription();
    private static final String TAG = "DRRID";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //create movie presenter
        if(moviePresenter == null){
            moviePresenter = new MoviePresenter();
        }

        //init imgs path and pass it to Presenter
        path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MOVIES);
        moviePresenter.setPath(path);


        // init RecyclerView
        moviesListRV = (RecyclerView)findViewById(R.id.rv_movies);
        movieAdapter = new MovieAdapter(myList, getApplication(), path);
        moviesListRV.setHasFixedSize(true);
        moviesListRV.setLayoutManager(new LinearLayoutManager(this));
        moviesListRV.setAdapter(movieAdapter);
        DividerItemDecoration dividerItemDecoration =
                new DividerItemDecoration(moviesListRV.getContext(),1);
        moviesListRV.addItemDecoration(dividerItemDecoration);
    }

    public void onBtnClick(View view) {
        pd = new ProgressDialog(this);
        pd.setMessage("Loading latest movies...");
        pd.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        pd.setIndeterminate(false);
        pd.setProgress(0);
//        pd.show();

        Observable<Movie> netObs = moviePresenter.getLMObs()
                .doOnNext(mvs -> updateItems(mvs))
                .flatMap(mvs -> Observable.from(mvs))
                .flatMap(mv -> moviePresenter.getInfoObs(mv));

        Observable<Movie> dbObs = db.loadMoviesObs()
                .doOnNext(mvs -> updateItems(mvs))
                .flatMap(mvs -> Observable.from(mvs));

        Observable.merge(dbObs, netObs).subscribe(mv -> updateItem(mv),
                throwable -> throwable.printStackTrace(),
                () -> cacheMovies());
    }


    private void updateItem(Movie movie) {
        Log.d(TAG, "updateItem: " + movie.getTitle());
        int i = myList.indexOf(movie);
        myList.remove(movie);
        myList.add(i, movie);
        movieAdapter.notifyDataSetChanged();
    }

    private void updateItems(List<Movie> movies) {
        myList.clear();
        myList.addAll(movies);
        movieAdapter.notifyDataSetChanged();
    }

    private void cacheMovies(){
        List<Movie> movies = myList;
        Observable.fromCallable(()->db.saveMovies(movies))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(d -> Log.d(TAG, "cacheMovies: " + d),
                        throwable ->throwable.printStackTrace());
    }
}
