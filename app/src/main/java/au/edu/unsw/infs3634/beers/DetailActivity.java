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
    private Beer mBeer;
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
        mName = findViewById(R.id.tvName);
        mShortDescription = findViewById(R.id.tvShortDescription);
        mDescription = findViewById(R.id.tvDescription);
        mABV = findViewById(R.id.tvABV);
        mIBU = findViewById(R.id.tvIBU);
        mSRM = findViewById(R.id.tvSRM);
        mBrewery = findViewById(R.id.tvBrewery);
        mSearch = findViewById(R.id.btSearch);

        Intent intent = getIntent();
        int position = intent.getIntExtra(MainActivity.EXTRA_MESSAGE, 0);
        mBeer = Beer.getDummyBeers().get(position);
        setTitle(mBeer.getName());
        mName.setText(mBeer.getName());
        mShortDescription.setText(mBeer.getShortDescription());
        mDescription.setText(mBeer.getDescription());
        mABV.setText(String.valueOf(mBeer.getAbv()) + "%");
        mIBU.setText("IBU: " + mBeer.getIbuMin() + " - " + mBeer.getIbuMax());
        mSRM.setText("SRM: " + mBeer.getSrmMin() + " - " + mBeer.getSrmMax());
        mBrewery.setText(mBeer.getBrewery());
        mSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchBeer(mBeer.getName());
            }
        });
    }

    private void searchBeer(String name) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.google.com/search?q=" + name));
        startActivity(intent);
    }
}
