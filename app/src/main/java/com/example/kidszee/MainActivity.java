package com.example.kidszee;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Find card views
        CardView cardNumbers = findViewById(R.id.cardNumbers);
        CardView cardAlphabet = findViewById(R.id.cardAlphabets);
        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) CardView cardshaping = findViewById(R.id.cardShape);
        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) CardView cardColor = findViewById(R.id.cardcolors);
        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) CardView cardPoem = findViewById(R.id.cardVocab);



        // Set click listeners
        cardNumbers.setOnClickListener(v -> {
            // Navigate to Numbers Activity
            Intent intent = new Intent(MainActivity.this, numbers.class);
            startActivity(intent);
        });

        cardAlphabet.setOnClickListener(v -> {
            // Navigate to Reading Activity
            Intent intent = new Intent(MainActivity.this, Alpha.class);
            startActivity(intent);
        });
        cardshaping.setOnClickListener(v -> {
            // Navigate to Numbers Activity
            Intent intent = new Intent(MainActivity.this, shapes.class);
            startActivity(intent);
        });
        cardColor.setOnClickListener(v -> {
            // Navigate to Numbers Activity
            Intent intent = new Intent(MainActivity.this, colors.class);
            startActivity(intent);
        });
        cardPoem.setOnClickListener(v -> {
            // Navigate to Numbers Activity
            Intent intent = new Intent(MainActivity.this, poem.class);
            startActivity(intent);
        });




    }
}