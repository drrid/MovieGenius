package rid.dr.tarek.moviegeniusr;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.util.Collections;
import java.util.List;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


/**
 * Created by Tarek on 11/27/2016.
 */

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {

    private List<Movie> myList = Collections.emptyList();
    private Context context;
    private File path;

    public MovieAdapter(List<Movie> myList, Context context,File path) {
        this.myList = myList;
        this.context = context;
        this.path = path;
    }

    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.movie_list_item,parent, false);
        MovieViewHolder v_holder = new MovieViewHolder(v);

        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int pos = v_holder.getAdapterPosition();
                Context context1 = view.getContext();
                Intent intent = new Intent(context1, DetailActivity.class);
                intent.putExtra("movie", myList.get(pos));
                context1.startActivity(intent);
            }
        });
        return v_holder;
    }

    @Override
    public void onBindViewHolder(MovieViewHolder holder, int position) {
        holder.title_list_item.setText(myList.get(position).getTitle());
        holder.year_list_item.setText(myList.get(position).getYear());
        holder.description_list_item.setText(myList.get(position).getDescription());

        if (myList.get(position).isHasPoster()){
            Observable.fromCallable(()->getBitmap(position))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(img->{
                        holder.poster_img_item.setImageBitmap(img);
                    });
        }
    }

    private Bitmap getBitmap(int position) {
        File file = new File(path, "/" + myList.get(position).getTitle()+".jpg");
        return BitmapFactory.decodeFile(file.getPath());
    }

    @Override
    public int getItemCount() {
        return myList.size();
    }

    public class MovieViewHolder extends RecyclerView.ViewHolder{
        TextView title_list_item;
        TextView year_list_item;
        TextView description_list_item;
        ImageView poster_img_item;

        public MovieViewHolder(View itemView) {
            super(itemView);
            title_list_item = (TextView)itemView.findViewById(R.id.tv_item_title);
            year_list_item = (TextView)itemView.findViewById(R.id.tv_item_year);
            description_list_item = (TextView)itemView.findViewById(R.id.tv_item_description);
            poster_img_item = (ImageView)itemView.findViewById(R.id.img_poster);
        }
    }
}
