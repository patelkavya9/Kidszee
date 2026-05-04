package com.example.kidszee;


import android.media.MediaPlayer;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.logging.Handler;

public class poemplayer extends AppCompatActivity {

    private MediaPlayer mediaPlayer;
    private TextView titleTextView;
    private ImageView poemImageView;
    private TextView lyricsTextView;
    private Button playButton;
    private SeekBar seekBar;
    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_poem);

        // Initialize views


        // Get poem details from intent
        String poemTitle = getIntent().getStringExtra("POEM_TITLE");
        int poemId = getIntent().getIntExtra("POEM_ID", 0);

        // Set poem title
        titleTextView.setText(poemTitle);

        // Load poem content
        loadPoemContent(poemId);

        // Setup play button
        playButton.setOnClickListener(v -> {
            if (mediaPlayer != null) {
                if (mediaPlayer.isPlaying()) {
                    mediaPlayer.pause();
                    playButton.setBackgroundResource(R.drawable.music);
                } else {
                    mediaPlayer.start();
                    playButton.setBackgroundResource(R.drawable.music);
                    updateSeekBar();
                }
            }
        });

        // Setup back button
        findViewById(R.id.backButton).setOnClickListener(v -> finish());
    }

    private void loadPoemContent(int poemId) {
        // Set poem image
        int imageResource = 0;
        int audioResource = 0;
        String lyrics = "";

        // Load poem content based on ID
        switch (poemId) {
            case 1: // The Rainbow
                imageResource = R.drawable.music;
                audioResource = R.raw.song_1;
                lyrics = getString(R.string.rainbow_poem);
                break;
           /* case 2: // My Little Garden
                imageResource = R.drawable.music;
                audioResource = R.raw.song_2;
                lyrics = getString(R.string.garden_poem);
                 break;*/
           /* case 3: // Stars at Night
                imageResource = R.drawable.music;
                audioResource = R.raw.song_3;
                lyrics = getString(R.string.stars_poem);
                break;*/
            // Add more cases for other poems
        }

        // Update UI
        if (imageResource != 0) {
            poemImageView.setImageResource(imageResource);
        }
        lyricsTextView.setText(lyrics);

        // Set up media player
        if (audioResource != 0) {
            setupMediaPlayer(audioResource);
        }
    }

    private void setupMediaPlayer(int audioResource) {
        // Release any existing MediaPlayer
        if (mediaPlayer != null) {
            mediaPlayer.release();
        }

        // Create and prepare a new MediaPlayer
        mediaPlayer = MediaPlayer.create(this, audioResource);
        mediaPlayer.setOnCompletionListener(mp -> {
            playButton.setBackgroundResource(R.drawable.music);
        });

        // Setup SeekBar
        seekBar.setMax(mediaPlayer.getDuration());
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser && mediaPlayer != null) {
                    mediaPlayer.seekTo(progress);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });
    }

    private void updateSeekBar() {
        if (mediaPlayer != null) {
            seekBar.setProgress(mediaPlayer.getCurrentPosition());


        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
       ;
    }
}
