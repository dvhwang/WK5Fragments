package au.edu.unsw.infs3634.beers;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class BeerAdapter extends RecyclerView.Adapter<BeerAdapter.BeerViewHolder> {
    private ArrayList<Beer> mBeers;
    private RecyclerViewClickListener mListener;

    public BeerAdapter(ArrayList<Beer> beers, RecyclerViewClickListener listener) {
        mBeers = beers;
        mListener = listener;
    }

    public interface RecyclerViewClickListener {
        void onClick(View view, int position);
    }

    public static class BeerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView name, shortDescription;
        private RecyclerViewClickListener mListener;

        public BeerViewHolder(View v, RecyclerViewClickListener listener) {
            super(v);
            mListener = listener;
            v.setOnClickListener(this);
            name = v.findViewById(R.id.tvName);
            shortDescription = v.findViewById(R.id.tvShortDescription);
        }

        @Override
        public void onClick(View view) {
            mListener.onClick(view, getAdapterPosition());
        }
    }

    @Override
    public BeerAdapter.BeerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.beer_list_row, parent, false);
        return new BeerViewHolder(v, mListener);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(BeerViewHolder holder, int position) {
        Beer beer = mBeers.get(position);
        holder.name.setText(beer.getName());
        holder.shortDescription.setText(beer.getShortDescription());

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mBeers.size();
    }

}
