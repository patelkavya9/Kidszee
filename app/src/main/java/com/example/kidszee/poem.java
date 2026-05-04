package com.example.kidszee;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class poem extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_poem);

        // Set click listeners for back button

    }

    private void setupPoemClickListeners() {
        // The Rainbow
        findViewById(R.id.poem1).setOnClickListener(v -> {
            openPoemPlayer("The Rainbow", 1);
        });

        // My Little Garden
        findViewById(R.id.poem2).setOnClickListener(v -> {
            openPoemPlayer("My Little Garden", 2);
        });

        // Stars at Night
        findViewById(R.id.poem3).setOnClickListener(v -> {
            openPoemPlayer("Stars at Night", 3);
        });

        // Add more click listeners for other poems
    }

    private void openPoemPlayer(String poemTitle, int poemId) {
        Intent intent = new Intent(this, poemplayer.class);
        intent.putExtra("POEM_TITLE", poemTitle);
        intent.putExtra("POEM_ID", poemId);
        startActivity(intent);
    }
}