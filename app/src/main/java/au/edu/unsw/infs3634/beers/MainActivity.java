package au.edu.unsw.infs3634.beers;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import au.edu.unsw.infs3634.beers.Entities.Beer;
import au.edu.unsw.infs3634.beers.Entities.BreweryDBResponse;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    private boolean mTwoPane;
    private BeerAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (findViewById(R.id.detail_container) != null) {
            mTwoPane = true;
        }

        RecyclerView mRecyclerView = findViewById(R.id.rvList);
        mRecyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new BeerAdapter(this, new ArrayList<Beer>(), mTwoPane);
        mRecyclerView.setAdapter(mAdapter);

        new GetBeerTask().execute();
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
            mAdapter.setBeers(beers);
            mAdapter.notifyDataSetChanged();
        }
    }
}
