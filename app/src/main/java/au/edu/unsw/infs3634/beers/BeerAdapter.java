package au.edu.unsw.infs3634.beers;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class BeerAdapter extends RecyclerView.Adapter<BeerAdapter.BeerViewHolder> {
    private MainActivity mParentActivity;
    private ArrayList<Beer> mBeers;
    private boolean mTwoPane;
    private View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Beer beer = (Beer) v.getTag();
            if(mTwoPane) {
                Bundle arguments = new Bundle();
                arguments.putString(DetailFragment.ARG_ITEM_ID, beer.getId());
                DetailFragment fragment = new DetailFragment();
                fragment.setArguments(arguments);
                mParentActivity.getSupportFragmentManager().beginTransaction().replace(R.id.detail_container, fragment).commit();
            } else {
                Context context = v.getContext();
                Intent intent = new Intent(context, DetailActivity.class);
                intent.putExtra(DetailFragment.ARG_ITEM_ID, beer.getId());
                context.startActivity(intent);
            }
        }
    };

    public BeerAdapter(MainActivity parent, ArrayList<Beer> beers, boolean twoPane) {
        mParentActivity = parent;
        mBeers = beers;
        mTwoPane = twoPane;
    }

    public static class BeerViewHolder extends RecyclerView.ViewHolder {
        public TextView name, shortDescription;

        public BeerViewHolder(View v) {
            super(v);
            name = v.findViewById(R.id.tvName);
            shortDescription = v.findViewById(R.id.tvShortDescription);
        }
    }

    @Override
    public BeerAdapter.BeerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.beer_list_row, parent, false);
        return new BeerViewHolder(v);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(BeerViewHolder holder, int position) {
        Beer beer = mBeers.get(position);
        holder.name.setText(beer.getName());
        holder.shortDescription.setText(beer.getShortDescription());
        holder.itemView.setTag(beer);
        holder.itemView.setOnClickListener(mOnClickListener);
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mBeers.size();
    }

}
