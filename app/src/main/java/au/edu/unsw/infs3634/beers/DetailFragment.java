package au.edu.unsw.infs3634.beers;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.io.IOException;
import java.util.List;

import au.edu.unsw.infs3634.beers.Entities.Beer;
import au.edu.unsw.infs3634.beers.Entities.BreweryDBResponse;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DetailFragment extends Fragment {
    public static final String ARG_ITEM_ID = "item_id";
    private Beer mBeer;

    public DetailFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(getArguments().containsKey(ARG_ITEM_ID)) {
            new GetBeerTask().execute();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_detail, container, false);
        updateUi();
        return rootView;
    }

    private void updateUi() {
        View rootView = getView();
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
    }

    private void searchBeer(String name) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.google.com/search?q=" + name));
        startActivity(intent);
    }

    private class GetBeerTask extends AsyncTask<Void, Void, List<Beer>> {
        @Override
        protected List<Beer> doInBackground(Void... voids) {
            try {
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("https://sandbox-api.brewerydb.com/v2/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                BeerService service = retrofit.create(BeerService.class);
                Call<BreweryDBResponse> beersCall = service.getBeers();
                Response<BreweryDBResponse> beerResponse = beersCall.execute();
                List<Beer> beers = beerResponse.body().getData();
                return beers;
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(List<Beer> beers) {
            for(Beer beer : beers) {
                if(beer.getId().equals(getArguments().getString(ARG_ITEM_ID))) {
                    mBeer = beer;
                    updateUi();
                }
            }
        }
    }
}
