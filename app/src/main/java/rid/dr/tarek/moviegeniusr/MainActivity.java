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
    RecyclerView moviesList;
    MovieAdapter movieAdapter;
    List<Movie> myList = new ArrayList<Movie>();
    MoviePresenter moviePresenter;
    ProgressDialog pd;
    File path;
    CompositeSubscription ss = new CompositeSubscription();

    @Override
    protected void onPause() {
//        ss.unsubscribe();
        super.onPause();

    }

    @Override
    public void onBackPressed() {
        if(pd.isShowing()){
            pd.dismiss();
            ss.unsubscribe();
        }
        super.onBackPressed();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if(moviePresenter == null){
            moviePresenter = new MoviePresenter();
        }
        path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MOVIES);

        //TODO: cach movies so when I enter the app I see the last downloaded info

        // init RecyclerView
        moviesList = (RecyclerView)findViewById(R.id.rv_movies);
        movieAdapter = new MovieAdapter(myList, getApplication(), path);

        moviesList.setHasFixedSize(true);
        moviesList.setLayoutManager(new LinearLayoutManager(this));
        moviesList.setAdapter(movieAdapter);
        DividerItemDecoration dividerItemDecoration =
                new DividerItemDecoration(moviesList.getContext(),1);
        moviesList.addItemDecoration(dividerItemDecoration);

        moviePresenter.setPath(path);
    }

    public void onBtnClick(View view) {
        pd = ProgressDialog.show(this, "Loading...", "Generating 1080p movies");
        Subscription s1 = Observable.fromCallable(()->moviePresenter.getLatestMovies())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(movies -> bgInfoDownload(movies))
                .subscribe(movies -> updateItems(movies),
                        throwable -> errPrint(throwable));
        ss.add(s1);
    }

    private void bgInfoDownload(List<Movie> movies){
        Subscription s2 = Observable.from(movies)
                .map(movie -> moviePresenter.getInfo(movie))
                .doOnCompleted(()->{
                    pd.dismiss();
                    bgPosterDownload(movies);
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(movie -> updateItem(movie),
                        throwable -> errPrint(throwable));

        ss.add(s2);
    }

    private void bgPosterDownload(List<Movie> movies){

        Subscription s3 = Observable.from(movies)
                .map(movie -> moviePresenter.getPoster(movie))
                .subscribeOn(Schedulers.io())
                .doOnNext(movie->bgThumbDownload(movie))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(movie -> updateItem(movie),
                        throwable -> errPrint(throwable));

        ss.add(s3);
    }

    private void bgThumbDownload(Movie movie) {
        Observable.fromCallable(() -> moviePresenter.getBackground(movie))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(mv -> updateItem(mv),
                        throwable -> errPrint(throwable));
    }

    private void errPrint(Throwable throwable) {
        Log.d("TITO", "errPrint: "+ throwable);
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
