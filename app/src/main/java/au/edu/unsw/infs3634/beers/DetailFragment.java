package au.edu.unsw.infs3634.beers;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.Gson;

import java.util.List;

import au.edu.unsw.infs3634.beers.Entities.Beer;
import au.edu.unsw.infs3634.beers.Entities.BreweryDBResponse;

public class DetailFragment extends Fragment {
    public static final String ARG_ITEM_ID = "item_id";
    private Beer mBeer;

    public DetailFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(getArguments().containsKey(ARG_ITEM_ID)) {
            Gson gson = new Gson();
            BreweryDBResponse response = gson.fromJson(BreweryDBResponse.json, BreweryDBResponse.class);
            List<Beer> beers = response.getData();
            for(Beer beer : beers) {
                if(beer.getId().equals(getArguments().getString(ARG_ITEM_ID))) {
                    mBeer = beer;
                }
            }
            this.getActivity().setTitle(mBeer.getName());
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_detail, container, false);

        if(mBeer != null) {
            ((TextView) rootView.findViewById(R.id.tvName)).setText(mBeer.getName());
            if(mBeer.getStyle() != null) {
                ((TextView) rootView.findViewById(R.id.tvShortDescription)).setText(mBeer.getStyle().getName());
                ((TextView) rootView.findViewById(R.id.tvIBU)).setText("IBU: " + mBeer.getStyle().getIbuMin() + " - " + mBeer.getStyle().getIbuMax());
                ((TextView) rootView.findViewById(R.id.tvSRM)).setText("SRM: " + mBeer.getStyle().getSrmMin() + " - " + mBeer.getStyle().getSrmMax());
            }
            ((TextView) rootView.findViewById(R.id.tvDescription)).setText(mBeer.getDescription());
            ((TextView) rootView.findViewById(R.id.tvABV)).setText(String.valueOf(mBeer.getAbv()) + "%");
            ((Button) rootView.findViewById(R.id.btSearch)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    searchBeer(mBeer.getName());
                }
            });
        }

        return rootView;
    }

    private void searchBeer(String name) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.google.com/search?q=" + name));
        startActivity(intent);
    }
}
