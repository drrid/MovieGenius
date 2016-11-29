package rid.dr.tarek.moviegeniusr;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Collections;
import java.util.List;


/**
 * Created by Tarek on 11/27/2016.
 */

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {

    List<Movie> myList = Collections.emptyList();
    Context context;

    public MovieAdapter(List<Movie> myList, Context context) {
        this.myList = myList;
        this.context = context;
    }

    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.movie_list_item,parent, false);
        MovieViewHolder v_holder = new MovieViewHolder(v);
        return v_holder;
    }

    @Override
    public void onBindViewHolder(MovieViewHolder holder, int position) {
        holder.title_list_item.setText(myList.get(position).getTitle());
        holder.year_list_item.setText(myList.get(position).getYear());
        holder.subInfo_list_item.setText(myList.get(position).getSubInfo());
        holder.description_list_item.setText(myList.get(position).getDescription());
    }

    @Override
    public int getItemCount() {
        return myList.size();
    }


    public class MovieViewHolder extends RecyclerView.ViewHolder{
        TextView title_list_item;
        TextView year_list_item;
        TextView subInfo_list_item;
        TextView description_list_item;

        public MovieViewHolder(View itemView) {
            super(itemView);
            title_list_item = (TextView)itemView.findViewById(R.id.tv_item_title);
            year_list_item = (TextView)itemView.findViewById(R.id.tv_item_year);
            subInfo_list_item = (TextView)itemView.findViewById(R.id.tv_item_subInfo);
            description_list_item = (TextView)itemView.findViewById(R.id.tv_item_description);

        }
    }
}
