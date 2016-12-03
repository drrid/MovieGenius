package rid.dr.tarek.moviegeniusr;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class DetailActivity extends AppCompatActivity {

    private DetailPresenter detailPresenter;
    private static final String TAG = "DRRID";
    Movie movie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        if(detailPresenter == null){
            detailPresenter = new DetailPresenter();
        }

        // init views
        ImageView poster = (ImageView)findViewById(R.id.poster);
        ImageView background = (ImageView)findViewById(R.id.background);

        TextView title = (TextView)findViewById(R.id.d_title);
        TextView year = (TextView)findViewById(R.id.d_year);
        TextView duration = (TextView)findViewById(R.id.d_duration);
        TextView rating = (TextView)findViewById(R.id.d_rating);
        TextView description = (TextView)findViewById(R.id.d_description);

        //get intent
        Intent intent = getIntent();
        movie = (Movie)intent.getSerializableExtra("movie");

        //set poster img
        if(movie.isHasPoster()){
            Observable.fromCallable(()->detailPresenter.setPosterImg(movie))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(img ->poster.setImageBitmap(img),
                            throwable -> Log.d(TAG, "setPoster: "+throwable));
        }

        //set background img
        if(movie.isHasBackground()){
            Observable.fromCallable(()->detailPresenter.setBackgroundImg(movie))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(img ->background.setImageBitmap(img),
                            throwable -> Log.d(TAG, "setBackground: "+throwable));
        }

        //set textviews
        title.setText(movie.getTitle());
        year.setText(movie.getYear());
        duration.setText(movie.getDuration());
        rating.setText(movie.getRating());
        description.setText(movie.getDescription());
    }

    public void onAddBtnClick(View view) {
        Log.d(TAG, "onAddBtnClick: tessssssssssst");
        Log.i("Send email", "");

        String[] TO = {"tarek.dr.rid@gmail.com"};
        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.setData(Uri.parse("mailto:"));
        emailIntent.setType("text/plain");


        emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "MovieGenius");
        emailIntent.putExtra(Intent.EXTRA_TEXT, movie.getTorrentURL());

        try {
            startActivity(Intent.createChooser(emailIntent, "Send mail..."));
            finish();
            Log.i("Finished sending email", "");
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(DetailActivity.this,
                    "There is no email client installed.", Toast.LENGTH_SHORT).show();
        }
    }
}
