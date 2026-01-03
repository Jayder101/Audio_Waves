package com.example.audiowaves;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    ListView songListView;
    TextView txtSongDetails;
    Button btnPlayPause;
    MediaPlayer mediaPlayer;

    // Arrays for song data
    String[] songTitles = {};
    String[] artistNames = {};
    int[] songResources = {R.raw.gunna_cfwm, R.raw.raw, R.raw.gunna_him_all_along, R.raw.gunna_showed_em, R.raw.gunnna_satisfaction_ft_asake, R.raw.gunna_wont_stop, R.raw.gunna_what_they_thinking, R.raw.gunna_made_for_this_shit, R.raw.gunna_made_for_this_shit};

    int currentSongIndex = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        songListView = findViewById(R.id.songListView);
        txtSongDetails = findViewById(R.id.txtSongDetails);
        btnPlayPause = findViewById(R.id.btnPlayPause);

        // Populate ListView
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, songTitles);
        songListView.setAdapter(adapter);

        // Handle Song Selection
        songListView.setOnItemClickListener((parent, view, position, id) -> {
            currentSongIndex = position;
            txtSongDetails.setText("Now Playing: " + songTitles[position] + " - " + artistNames[position]);
            prepareMusic(songResources[position]);
        });

        // Handle Play/Pause Button
        btnPlayPause.setOnClickListener(v -> togglePlayback());
    }

    private void prepareMusic(int resourceId) {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
        }
        mediaPlayer = MediaPlayer.create(this, resourceId);
        mediaPlayer.start();
        btnPlayPause.setText("PAUSE");
    }

    private void togglePlayback() {
        if (mediaPlayer == null) return;

        if (mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
            btnPlayPause.setText("PLAY");
        } else {
            mediaPlayer.start();
            btnPlayPause.setText("PAUSE");
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }
}