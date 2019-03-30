package au.edu.unsw.infs3634.beers;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.google.gson.Gson;

import java.util.List;

import au.edu.unsw.infs3634.beers.Entities.Beer;
import au.edu.unsw.infs3634.beers.Entities.BreweryDBResponse;

public class MainActivity extends AppCompatActivity {
    private boolean mTwoPane;

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

        Gson gson = new Gson();
        BreweryDBResponse response = gson.fromJson(BreweryDBResponse.json, BreweryDBResponse.class);
        List<Beer> beers = response.getData();

        RecyclerView.Adapter mAdapter = new BeerAdapter(this, beers, mTwoPane);
        mRecyclerView.setAdapter(mAdapter);
    }
}
