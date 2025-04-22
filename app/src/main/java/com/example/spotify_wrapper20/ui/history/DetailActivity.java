package com.example.spotify_wrapper20.ui.history;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.spotify_wrapper20.R;


public class DetailActivity extends AppCompatActivity {
    TextView detailTopArtists, detailTopTracks, detailTopGenres, detailDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Toolbar toolbar = findViewById(R.id.toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true); // Add the back arrow
        }

        detailTopArtists = findViewById(R.id.detailTopArtists);
        detailTopTracks = findViewById(R.id.detailTopTracks);
        detailTopGenres = findViewById(R.id.detailTopGenres);
        detailDate = findViewById(R.id.detailDateWrapped);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            String date = bundle.getString("date");
            String topArtists = bundle.getString("topArtists");
            String topTracks = bundle.getString("topTracks");
            String topGenres = bundle.getString("topGenres");

            detailDate.setText(date);
            detailTopArtists.setText(topArtists);
            detailTopTracks.setText(topTracks);
            detailTopGenres.setText(topGenres);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}