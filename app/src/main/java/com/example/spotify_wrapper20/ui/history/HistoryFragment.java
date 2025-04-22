package com.example.spotify_wrapper20.ui.history;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.spotify_wrapper20.R;
import com.example.spotify_wrapper20.ui.wrapped.WrappedFragment;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;


public class HistoryFragment extends Fragment {
    private RecyclerView recyclerView;
    private List<DataClass> dataList;
    private SearchView searchView;
    DataClass androidData;
    private MyAdapter adapter;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_history, container, false);


        // Initialize views
        recyclerView = rootView.findViewById(R.id.recyclerView);
        searchView = rootView.findViewById(R.id.search);

        sharedPreferences = getActivity().getSharedPreferences("data", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        // Retrieve data from SharedPreferences
        retrieveDataFromSharedPreferences();
//
        // Initialize data and adapter
        String currentDate = new SimpleDateFormat("MM-dd-yyyy", Locale.getDefault()).format(new Date());
        String topArtists = WrappedFragment.getTopArtists();
        String topTracks = WrappedFragment.getTopTracks();
        String topGenres = WrappedFragment.getTopGenres();
        dataList = new ArrayList<>();
        androidData = new DataClass(currentDate, topArtists, topTracks, topGenres);
        dataList.add(androidData);
        adapter = new MyAdapter(getContext(), dataList);
        recyclerView.setAdapter(adapter);
//
        searchView.clearFocus();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                searchList(newText);
                return true;
            }
        });



        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 1);
        recyclerView.setLayoutManager(gridLayoutManager);

        retrieveDataFromSharedPreferences();

        return rootView;
    }

    // Method to receive data from HomeFragment
    public void setDataFromHomeFragment(String date, String artist, String songs, String genres) {
        androidData = new DataClass(date, artist, songs, genres);
        dataList.add(androidData);
        adapter.notifyItemInserted(dataList.size() - 1);
        addDataToHistory(date, artist, songs, genres);
    }

    private void retrieveDataFromSharedPreferences() {
        String json = sharedPreferences.getString("data", "");
        Type type = new TypeToken<List<DataClass>>(){}.getType();
        dataList = new ArrayList<>();
        if (!json.isEmpty()) {
            dataList = new Gson().fromJson(json, type);
        }
    }

    private void saveDataToPreferences(String currentDate, String topArtists, String topTracks, String topGenres) {
        SharedPreferences sharedPreferences = requireActivity().getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("date", currentDate);
        editor.putString("topArtists", topArtists);
        editor.putString("topTracks", topTracks);
        editor.putString("topGenres", topGenres);
        editor.apply();
    }

    private void searchList(String text){
        List<DataClass> dataSearchList = new ArrayList<>();
        for (DataClass data : dataList){
            if (data.getDataDate().toLowerCase().contains(text.toLowerCase())) {
                dataSearchList.add(data);
            }
        }
        if (!dataSearchList.isEmpty()){
            adapter.setSearchList(dataSearchList);
        }
    }

    public void addDataToHistory(String currentDate, String topArtists, String topTracks, String topGenres) {
        androidData.setDataDate(currentDate);
        androidData.setDataArtist(topArtists);
        androidData.setDataSongs(topTracks);
        androidData.setDataGenres(topGenres);

        // Add androidData to dataList
        dataList.add(androidData);

        // Notify the adapter of the new item insertion
        adapter.notifyItemInserted(dataList.size() - 1);
    }
}