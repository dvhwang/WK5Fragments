package au.edu.unsw.infs3634.beers;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class DetailActivity extends AppCompatActivity {
    private TextView mName;
    private TextView mShortDescription;
    private TextView mDescription;
    private TextView mABV;
    private TextView mIBU;
    private TextView mSRM;
    private TextView mBrewery;
    private Button mSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Intent intent = getIntent();
        String beerId = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);

        ArrayList<Beer> beers = Beer.getDummyBeers();
        for(final Beer beer : beers) {
            if(beer.getId().equals(beerId)) {
                mName = findViewById(R.id.tvName);
                mShortDescription = findViewById(R.id.tvShortDescription);
                mDescription = findViewById(R.id.tvDescription);
                mABV = findViewById(R.id.tvABV);
                mIBU = findViewById(R.id.tvIBU);
                mSRM = findViewById(R.id.tvSRM);
                mBrewery = findViewById(R.id.tvBrewery);
                mSearch = findViewById(R.id.btSearch);

                setTitle(beer.getName());
                mName.setText(beer.getName());
                mShortDescription.setText(beer.getShortDescription());
                mDescription.setText(beer.getDescription());
                mABV.setText(String.valueOf(beer.getAbv()) + "%");
                mIBU.setText("IBU: " + beer.getIbuMin() + " - " + beer.getIbuMax());
                mSRM.setText("SRM: " + beer.getSrmMin() + " - " + beer.getSrmMax());
                mBrewery.setText(beer.getBrewery());
                mSearch.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        searchBeer(beer.getName());
                    }
                });
            }
        }
    }

    private void searchBeer(String name) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.google.com/search?q=" + name));
        startActivity(intent);
    }
}
