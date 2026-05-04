package com.example.kidszee;

import android.annotation.SuppressLint;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.concurrent.TimeUnit;

public class Media extends AppCompatActivity {

    TextView startTime;
    TextView endTime;
    SeekBar seekBar;
    MediaPlayer mediaPlayer;
    Button pauseButton;
    Button forwardButton;
    Button playButton;
    Button backButton;
    Handler handler;
    Runnable runnable;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_media);

        TextView songTitle = findViewById(R.id.song_title);
        startTime = findViewById(R.id.start_time);
        endTime = findViewById(R.id.end_time);
        seekBar = findViewById(R.id.seek_bar);
        playButton = findViewById(R.id.play_button);
        pauseButton = findViewById(R.id.pause_button);
        forwardButton = findViewById(R.id.forward_button);
        backButton = findViewById(R.id.back_button);

        handler = new Handler();


        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String title = extras.getString("title");
            int songResourceId = extras.getInt("songResourceId");

            songTitle.setText(title);
            mediaPlayer = MediaPlayer.create(this, songResourceId);
            mediaPlayer.setLooping(true);

            setupSeekBar();

            playButton.setOnClickListener(v -> {
                if (!mediaPlayer.isPlaying()) {
                    mediaPlayer.start();
                    updateSeekBar();
                }
            });

            pauseButton.setOnClickListener(v -> {
                if (mediaPlayer.isPlaying()) {
                    mediaPlayer.pause();
                }
            });

            forwardButton.setOnClickListener(v -> {
                int currentPosition = mediaPlayer.getCurrentPosition();
                int duration = mediaPlayer.getDuration();
                if (currentPosition + 5000 < duration) {
                    mediaPlayer.seekTo(currentPosition + 5000);
                }
            });

            backButton.setOnClickListener(v -> {
                int currentPosition = mediaPlayer.getCurrentPosition();
                mediaPlayer.seekTo(Math.max(currentPosition - 5000, 0));
            });
        }
    }

    private void setupSeekBar() {
        seekBar.setMax(mediaPlayer.getDuration());

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    mediaPlayer.seekTo(progress);
                }

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        runnable = new Runnable() {
            @SuppressLint("DefaultLocale")
            @Override
            public void run() {
                if (mediaPlayer != null) {
                    int currentPosition = mediaPlayer.getCurrentPosition();
                    seekBar.setProgress(currentPosition);

                    startTime.setText(String.format("%d:%02d",
                            TimeUnit.MILLISECONDS.toMinutes(currentPosition),
                            TimeUnit.MILLISECONDS.toSeconds(currentPosition) -
                                    TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(currentPosition))
                    ));

                    endTime.setText(String.format("%d:%02d",
                            TimeUnit.MILLISECONDS.toMinutes(mediaPlayer.getDuration()),
                            TimeUnit.MILLISECONDS.toSeconds(mediaPlayer.getDuration()) -
                                    TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(mediaPlayer.getDuration()))
                    ));

                    handler.postDelayed(this, 1000);
                }
            }
        };
        handler.postDelayed(runnable, 0);

    }

    private void updateSeekBar() {
        seekBar.setProgress(mediaPlayer.getCurrentPosition());
        if (mediaPlayer.isPlaying()) {
            handler.postDelayed(runnable, 1000);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
        handler.removeCallbacks(runnable);
    }
}

//package com.example.kidszee;
//
//import android.os.Bundle;
//
//import androidx.activity.EdgeToEdge;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.core.graphics.Insets;
//import androidx.core.view.ViewCompat;
//import androidx.core.view.WindowInsetsCompat;
//
//public class MediaActivity extends AppCompatActivity {
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        EdgeToEdge.enable(this);
//        setContentView(R.layout.activity_media);
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
//            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
//            return insets;
//        });
//    }
//}