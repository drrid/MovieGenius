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

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

public class MainActivity extends AppCompatActivity {

    private RecyclerView moviesListRV;
    private MovieAdapter movieAdapter;
    private List<Movie> myList = new ArrayList<Movie>();
    private MoviePresenter moviePresenter;
    private ProgressDialog pd;
    private File path;
    private CompositeSubscription ss = new CompositeSubscription();
    private static final String TAG = "DRRID";

    //TODO: create observable for items and subscribe to it from detailActivity

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

        //TODO: cach movies so when I enter the app I see the last downloaded info

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
        pd = ProgressDialog.show(this, "Loading...", "Generating 1080p movies");
        Subscription s1 = Observable
                .fromCallable(()->moviePresenter.getLatestMovies())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(movies -> updateItems(movies))
                .retry()
                .subscribe(movies -> bgInfoDownload(movies),
                        throwable -> errPrint(throwable));
        ss.add(s1);
    }

    private void bgInfoDownload(List<Movie> movies){
        Subscription s2 = Observable
                .from(movies)
                .map(movie -> moviePresenter.getInfo(movie))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())

                .subscribe(mv-> bgGetTorrent(mv),
                        throwable -> errPrint(throwable));
        ss.add(s2);
    }

    private void bgGetTorrent(Movie movie){
        Observable.fromCallable(()->moviePresenter.getTorrent(movie))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(mv-> bgPosterDownload(mv))
                .subscribe(mv->updateItem(mv),
                        throwable -> errPrint(throwable));
    }

    private void bgPosterDownload(Movie movie){
        Subscription s3 = Observable
                .fromCallable(() -> moviePresenter.getPoster(movie))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnCompleted(()->pd.dismiss())
                .subscribe(mv->updateItem(mv),
                        throwable -> errPrint(throwable));
        ss.add(s3);
    }

    private void bgThumbDownload(Movie movie){
        Subscription s4 = Observable
                .fromCallable(() -> moviePresenter.getBackground(movie))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(mv -> updateItem(mv),
                        throwable -> errPrint(throwable));
        ss.add(s4);
    }


    private void errPrint(Throwable throwable) {
        Log.d(TAG, "errPrint: "+ throwable);
    }

    private void updateItem(Movie movie) {
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
}
