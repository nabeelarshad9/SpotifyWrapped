package com.example.spotify_wrapper20.ui.wrapped;

import android.content.Intent;
import android.media.AudioAttributes;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.spotify_wrapper20.MainActivity;
import com.example.spotify_wrapper20.R;
import com.example.spotify_wrapper20.ui.history.HistoryFragment;
import com.example.spotify_wrapper20.ui.history.DataClass;
import com.example.spotify_wrapper20.ui.history.DetailActivity;
import com.example.spotify_wrapper20.ui.history.MyAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class WrappedFragment extends Fragment {

//    private FragmentHomeBinding binding;
    private static final String CLIENT_ID = "c374f36771d14d4db7961a58b24ca5be";
    private static final String REDIRECT_URI = "spotify-wrapper://auth";
    private static final int AUTH_REQUEST_CODE = 1;

    private MediaPlayer mediaPlayer;
    
    private final OkHttpClient okHttpClient = new OkHttpClient();

    private Call call;

    private String accessToken;
    private WrappedViewModel viewModel;

    private MyAdapter adapter;

    private List<DataClass> dataList;

    private RecyclerView recyclerView;
    private Button med,all,srt, saveToHistory;;
    private ImageButton notifs;
    public static Boolean notifsOff = false;
    public static TextView nameTextView, genreTextView, profileTextView, artistListTextView, songListTextView, holidayTrackTextView,TopHolidayTrackTextView;
    boolean holidayFlag = false;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        notifsOff = false;
        RelativeLayout layout = view.findViewById(R.id.relativeLayout);
        int backgroundResource;
        Calendar calendar = Calendar.getInstance();
        int month = calendar.get(Calendar.MONTH);
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

        // Checks if it's Christmas Day (December 31st)
        if (month == Calendar.DECEMBER && dayOfMonth == 25) {
            if (MainActivity.isNightModeOn) {
                backgroundResource = R.drawable.dark_inside_christmas_background;
            } else {
                backgroundResource = R.drawable.inside_christmas_background;
            }
        }
        else if (month == Calendar.OCTOBER && dayOfMonth == 31) {
            if (MainActivity.isNightModeOn) {
                backgroundResource = R.drawable.dark_inside_halloween_background;
            } else {
                backgroundResource = R.drawable.inside_halloween_background;
            }
        }
        else if (month == Calendar.JANUARY && dayOfMonth == 1) {
            if (MainActivity.isNightModeOn) {
                backgroundResource = R.drawable.dark_inside_new_year;
            } else {
                backgroundResource = R.drawable.inside_new_year_background;
            }
        }
        else if (month == Calendar.FEBRUARY && dayOfMonth == 14) {
            if (MainActivity.isNightModeOn) {
                backgroundResource = R.drawable.dark_inside_valentines_day_background;
            } else {
                backgroundResource = R.drawable.inside_valentines_day_background;
            }
        }
        else if (month == Calendar.JULY && dayOfMonth == 4) {
            if (MainActivity.isNightModeOn) {
                backgroundResource = R.drawable.dark_inside_fourth_of_july_background;
            } else {
                backgroundResource = R.drawable.inside_fourth_of_july_background;
            }
        }
        else{
            if (MainActivity.isNightModeOn) {
//                backgroundResource = R.drawable.dark_inside_christmas_background;
                backgroundResource = R.drawable.dark_inside_purple_background;
            } else {
//                backgroundResource = R.drawable.inside_christmas_background;
                backgroundResource = R.drawable.inside_purple_background;
            }
        }
        layout.setBackgroundResource(backgroundResource);

        CardView artist = (CardView) view.findViewById(R.id.topCard);
        artist.setVisibility(View.GONE);
        CardView topTrack = (CardView) view.findViewById(R.id.toptrack);
        topTrack.setVisibility(View.GONE);

        nameTextView = (TextView) view.findViewById(R.id.nameTextView);
        profileTextView = (TextView) view.findViewById(R.id.response_text_view);
        artistListTextView = (TextView) view.findViewById(R.id.artistList_text_view);
        songListTextView = (TextView) view.findViewById(R.id.songsList_text_view);
        genreTextView = (TextView) view.findViewById(R.id.genre_text_view);
        holidayTrackTextView = (TextView) view.findViewById(R.id.holiday_track_text_view);
        TopHolidayTrackTextView = (TextView) view.findViewById(R.id.topHolidayTracks);
        viewModel = new ViewModelProvider(getActivity()).get(WrappedViewModel.class);
        accessToken = viewModel.getAccessToken();
        cancelCall();
        makeUserProfile();

        all = view.findViewById(R.id.all);
        med = view.findViewById(R.id.medium);
        srt = view.findViewById(R.id.srt);
        saveToHistory = view.findViewById(R.id.saveToHistory);
        ScrollView scroll = view.findViewById(R.id.scrollview);


        all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makeArtistProfile("long_term");
                makeTasteProfile("long_term");
                artist.setVisibility(View.VISIBLE);
                topTrack.setVisibility(View.VISIBLE);

                scroll.post(new Runnable() {
                    @Override
                    public void run() {
                        scroll.fullScroll(ScrollView.FOCUS_UP);
                    }
                });
            }
        });

        med.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makeArtistProfile("medium_term");
                makeTasteProfile("medium_term");
                artist.setVisibility(View.VISIBLE);
                topTrack.setVisibility(View.VISIBLE);
                scroll.post(new Runnable() {
                    @Override
                    public void run() {
                        scroll.fullScroll(ScrollView.FOCUS_UP);
                    }
                });
            }
        });

        srt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makeArtistProfile("short_term");
                makeTasteProfile("short_term");
                artist.setVisibility(View.VISIBLE);
                topTrack.setVisibility(View.VISIBLE);
                scroll.post(new Runnable() {
                    @Override
                    public void run() {
                        scroll.fullScroll(ScrollView.FOCUS_UP);
                    }
                });
            }
        });

        notifs = view.findViewById(R.id.notif);
        notifs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!notifsOff){
                    notifsOff = true;
                    Toast.makeText(getActivity(), "Notifications turned off", Toast.LENGTH_SHORT).show();
                }
                else {
                    notifsOff = false;
                    Toast.makeText(getActivity(), "Notifications turned on", Toast.LENGTH_SHORT).show();
                }

            }
        });

        String textFromArtistListTextView = artistListTextView.getText().toString();

        // Extracting the first line
        String[] lines = textFromArtistListTextView.split("\\n");
        String firstLine = "";
        if (lines.length > 0) {
            firstLine = lines[0];
        }

        dataList = new ArrayList<>();
        View view1 = inflater.inflate(R.layout.fragment_history, container,false);

        recyclerView = view1.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new MyAdapter(getActivity(), dataList);
        recyclerView.setAdapter(adapter);

        saveToHistory.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String currentDate = getCurrentDate();
                String topArtists = artistListTextView.getText().toString();
                String topTracks = songListTextView.getText().toString();
                String topGenres = genreTextView.getText().toString();

                Intent intent = new Intent(getActivity(), DetailActivity.class);
                intent.putExtra("date", currentDate);
                intent.putExtra("topArtists", topArtists);
                intent.putExtra("topTracks", topTracks);
                intent.putExtra("topGenres", topGenres);
                startActivity(intent);
            }
        });
        return view;
    }

    private void sendDataToDashboardFragment(String currentDate, String topArtists, String topSongs, String topGenres) {
        HistoryFragment historyFragment = (HistoryFragment) getParentFragmentManager().findFragmentById(R.id.navigation_history);
        if (historyFragment != null) {
            historyFragment.setDataFromHomeFragment(currentDate, topArtists, topSongs, topGenres);
        }
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    private String getCurrentDate() {
        String currentDate = new SimpleDateFormat("MM-dd-yyyy", Locale.getDefault()).format(new Date());
        return currentDate;
    }
    private void makeUserProfile() {
        if (accessToken == null) {
            //Toast.makeText(getActivity(), "You need to get an access token first!", Toast.LENGTH_SHORT).show();
            return;
        }
        Request request = new Request.Builder()
                .url("https://api.spotify.com/v1/me")
                .addHeader("Authorization", "Bearer " + accessToken)
                .build();
        //cancelCall();
        call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("HTTP", "Failed to fetch data: " + e);
//                Toast.makeText(getActivity(), "Failed to fetch data, watch Logcat for more details",
//                        Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    final JSONObject jsonObject = new JSONObject(response.body().string());
                    final String username = jsonObject.getString("display_name");
                    setTextAsync(username, nameTextView);
                    //setTextAsync(jsonObject.getString("email"), emailTextView);
                    //setTextAsync(jsonObject.toString(3), profileTextView);
                } catch (JSONException e) {
                    Log.d("JSON", "Failed to parse data: " + e);
//                    Toast.makeText(getActivity(), "Failed to parse data, watch Logcat for more details",
//                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    private void makeArtistProfile(String timeRange) {
        if (accessToken == null) {
            //Toast.makeText(getActivity(), "You need to get an access token first!", Toast.LENGTH_SHORT).show();
            return;
        }
        Request request = new Request.Builder()
                .url("https://api.spotify.com/v1/me/top/artists?time_range=" + timeRange)
                .addHeader("Authorization", "Bearer " + accessToken)
                .build();
        //cancelCall();
        call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("HTTP", "Failed to fetch data: " + e);
//                Toast.makeText(getActivity(), "Failed to fetch data, watch Logcat for more details",
//                        Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    String jsonResponse = response.body().string();
                    final JSONObject jsonObject = new JSONObject(jsonResponse);
                    Log.d("API", "Response: " + jsonResponse);
                    JSONArray items = jsonObject.getJSONArray("items");
                    String artistList = "";
                    Map<String, Integer> genreCounts = new HashMap<>();
                    for (int i = 0; i < items.length(); i++) {
                        JSONObject artist = items.getJSONObject(i);
                        if (i < 5) {
                            String name = artist.getString("name");
                            artistList = artistList + (i + 1) + ". " + name + "\n";
                        }
                        JSONArray genres = artist.getJSONArray("genres");
                        for (int j = 0; j < genres.length(); j++) {
                            String genre = genres.getString(j);
                            if (genreCounts.containsKey(genre)) {
                                genreCounts.put(genre, genreCounts.get(genre) + 1);
                            } else {
                                genreCounts.put(genre, 1);
                            }
                        }
                    }
                    List<Map.Entry<String, Integer>> sortedGenres = new ArrayList<>(genreCounts.entrySet());
                    Collections.sort(sortedGenres, (o1, o2) -> o2.getValue().compareTo(o1.getValue()));
                    String topGenre = "You seem to be on a strong " + sortedGenres.get(0).getKey() + " and " + sortedGenres.get(1).getKey() + " vibe.";
                    setTextAsync(artistList, artistListTextView);
                    setTextAsync(topGenre, genreTextView);
                } catch (JSONException e) {
                    Log.d("JSON", "Failed to parse data: " + e);
//                    Toast.makeText(getActivity(), "Failed to parse data, watch Logcat for more details",
//                            Toast.LENGTH_SHORT).show();
                }
            }
        });

        Calendar calendar = Calendar.getInstance();
        int month = calendar.get(Calendar.MONTH);
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
        String spotifyUrl = "";
        // Checks if it's Christmas Day (December 31st)
//        if (month == Calendar.APRIL && dayOfMonth == 20) {
        if (month == Calendar.DECEMBER && dayOfMonth == 25) {
            holidayFlag = true;
            setTextAsync("Top Christmas Tracks", TopHolidayTrackTextView);
            spotifyUrl = "https://api.spotify.com/v1/playlists/40JqiFNlPV95wv3Fg2cFat";
        }
        else if (month == Calendar.OCTOBER && dayOfMonth == 31) {
//        else if (month == Calendar.APRIL && dayOfMonth == 20) {
            holidayFlag = true;
            setTextAsync("Top Halloween Tracks", TopHolidayTrackTextView);
            spotifyUrl = "https://api.spotify.com/v1/playlists/0LMaBQ9WqYu9GWi36Tt0ag?market=US";
//            spotifyUrl = "https://api.spotify.com/v1/search?q=genre%3AHalloween&type=track&limit=5";
        }
        else if (month == Calendar.JANUARY && dayOfMonth == 1) {
//        else if (month == Calendar.APRIL && dayOfMonth == 20) {
            holidayFlag = true;
            setTextAsync("Top New Year Tracks", TopHolidayTrackTextView);
            spotifyUrl = "https://api.spotify.com/v1/playlists/1wnsAaslIwe1Dg1RnLG6jJ";
        }
        else if (month == Calendar.FEBRUARY && dayOfMonth == 14) {
//        else if (month == Calendar.APRIL && dayOfMonth == 20) {
            holidayFlag = true;
            setTextAsync("Top Valentine's Day Tracks", TopHolidayTrackTextView);
            spotifyUrl = "https://api.spotify.com/v1/playlists/34ewmToE2CiAM2MQUauN9m";
        }
        else if (month == Calendar.JULY && dayOfMonth == 4) {
//        else if (month == Calendar.APRIL && dayOfMonth == 20) {
            holidayFlag = true;
            setTextAsync("Top Independence Day Tracks", TopHolidayTrackTextView);
            spotifyUrl = "https://api.spotify.com/v1/playlists/6JYBhmQS2p89fmT1bJMdzi";
        }
        else{
            holidayFlag = false;
            TopHolidayTrackTextView.setVisibility(View.GONE);
            holidayTrackTextView.setVisibility(View.GONE);
        }
        if (holidayFlag) {
            Request holidayRequest = new Request.Builder()
                    .url(spotifyUrl)
                    .addHeader("Authorization", "Bearer " + accessToken)
                    .build();
            call = okHttpClient.newCall(holidayRequest);
            call.enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    Log.d("HTTP", "Failed to fetch data: " + e);
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    try {
                        String jsonResponse = response.body().string();
                        final JSONObject jsonObject = new JSONObject(jsonResponse);
                        Log.d("API", "Response: " + jsonResponse);
                        JSONArray tracks = jsonObject.getJSONObject("tracks").getJSONArray("items");
                        String holidayTrackList = "";
                        for (int i = 0; i < tracks.length(); i++) {
                            JSONObject track = tracks.getJSONObject(i).getJSONObject("track");
//                            Random holidayRand = new Random();
//                            String holiday_preview_url = tracks.getJSONObject(holidayRand.nextInt(5)).getJSONObject("track").getString("preview_url");
                            if (i == 0) {
                                String holiday_preview_url = track.getString("preview_url");
                                playTrackPreview(holiday_preview_url);
//                                holidayTrackList += (i + 1) + ". " + holiday_preview_url + "\n";
                            }
                            String holidayTrackName = track.getString("name");
                            holidayTrackList += (i + 1) + ". " + holidayTrackName + "\n";
                            JSONArray artists = track.getJSONArray("artists");
                            String artistNames = "";
                            for (int j = 0; j < artists.length(); j++) {
                                JSONObject artist = artists.getJSONObject(j);
                                artistNames += artist.getString("name");
                                if (j < artists.length() - 1) {
                                    artistNames += ", ";
                                }
                            }
                        }

                        setTextAsync(holidayTrackList, holidayTrackTextView);
                    } catch (JSONException e) {
                        Log.d("JSON", "Failed to parse data: " + e);
                    }
                }
            });
        }
    }
    private void makeTasteProfile(String timeRange) {
        if (accessToken == null) {
            Toast.makeText(getActivity(), "You need to get an access token first!", Toast.LENGTH_SHORT).show();
            return;
        }
        Request request = new Request.Builder()
                .url("https://api.spotify.com/v1/me/top/tracks?time_range=" + timeRange)
                .addHeader("Authorization", "Bearer " + accessToken)
                .build();
        //cancelCall();
        call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("HTTP", "Failed to fetch data: " + e);
                Toast.makeText(getActivity(), "Failed to fetch data, watch Logcat for more details",
                        Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    String jsonResponse = response.body().string();
//                    Log.d("API", "Response: " + jsonResponse);
                    final JSONObject jsonObject = new JSONObject(jsonResponse);
                    JSONArray items = jsonObject.getJSONArray("items");
                    JSONObject trackObject = items.getJSONObject(0);
                    String trackName = trackObject.getString("name");

                    //Gets track preview url to play
                    if (!holidayFlag) {
                        Random rand = new Random();
                        String preview_url = items.getJSONObject(rand.nextInt(5)).getString("preview_url");
                        playTrackPreview(preview_url);
                    }
                    JSONArray artistsArray = trackObject.getJSONArray("artists");
                    JSONObject artistObject = artistsArray.getJSONObject(0);
                    String artistName = artistObject.getString("name");
                    String topTrack = "Your top track is \"" + trackName + "\" by " + artistName + ". ";

                    Map<String, Integer> artistCounts = new HashMap<>();

                    for (int i = 0; i < items.length(); i++) {
                        JSONObject track = items.getJSONObject(i);
                        JSONArray artists = track.getJSONArray("artists");
                        for (int j = 0; j < artists.length(); j++) {
                            JSONObject artistO = artists.getJSONObject(j);
                            String artist = artistO.getString("name");
                            if(artistCounts.containsKey(artist)) {
                                artistCounts.put(artist, artistCounts.get(artist) + 1);
                            } else {
                                artistCounts.put(artist, 1);
                            }
                        }
                    }
                    List<Map.Entry<String, Integer>> sortedArtists = new ArrayList<>(artistCounts.entrySet());
                    Collections.sort(sortedArtists, (o1, o2) -> o2.getValue().compareTo(o1.getValue()));
                    List<String> topArtists = new ArrayList<>();
                    for(int i = 0; i < Math.min(sortedArtists.size(), 5); i++) {
                        topArtists.add(sortedArtists.get(i).getKey());
                    }

                    String topArtist = "You seem to be listening to a lot of " + topArtists.get(0)   + " lately. ";
                    String artistList = "";
                    for (int i = 0; i < topArtists.size(); ++i) {
                        artistList = artistList + (i+1) + ". " + topArtists.get(i) + "\n";
                    }
                    String songList = "";
                    for (int i = 0; i < 5; i++) {
                        JSONObject o = items.getJSONObject(i);
                        String newName = o.getString("name");
                        JSONArray a = o.getJSONArray("artists");
                        JSONObject aO = a.getJSONObject(0);
                        String aName = aO.getString("name");
                        songList = songList + (i+1) + ". " + newName + " by " + aName + "\n";
                    }
                    setTextAsync(topTrack + topArtist, profileTextView);
                    setTextAsync(songList, songListTextView);
                } catch (JSONException e) {
                    Log.d("JSON", "Failed to parse data: " + e);
//                    Toast.makeText(getActivity(), "Failed to parse data, watch Logcat for more details",
//                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void playTrackPreview(String track_url) {
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
            mediaPlayer.reset();
            mediaPlayer.release();
            mediaPlayer = null;
        }
        mediaPlayer = new MediaPlayer();

        try {
            mediaPlayer.setDataSource(track_url);
            mediaPlayer.setAudioAttributes(new AudioAttributes.Builder()
                    .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                    .setUsage(AudioAttributes.USAGE_MEDIA)
                    .build());
            mediaPlayer.prepare();
            mediaPlayer.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void setTextAsync(final String text, TextView textView) {
        getActivity().runOnUiThread(() -> textView.setText(text));
    }
    private void cancelCall() {
        if (call != null) {
            call.cancel();
        }
    }
    public void onDestroy() {
        super.onDestroy();
        cancelCall();
    }
    public static String getTopArtists() {
        // Extract the top artists from the TextView
        String topArtists = artistListTextView.getText().toString();
        // Remove any extra whitespace and newline characters
        return topArtists;
    }

    public static String getTopTracks() {
        // Extract the top tracks from the TextView
        String topTracks = songListTextView.getText().toString();
        return topTracks;
    }

    public static String getTopGenres() {
        // Extract the top genres from the TextView
        String topGenres = genreTextView.getText().toString();
        return topGenres;
    }
}