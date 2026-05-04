package com.example.kidszee;



import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Alpha extends AppCompatActivity {

    // Array of letter names and corresponding words
    private final String[] LETTERS = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M",
            "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};

    private final String[] WORDS = {"AXE", "BALL", "CAT", "DOG", "EGG", "FISH", "GOAT", "HAT",
            "ICE CREAM", "JUG", "KEY", "LION", "MONKEY", "NEST", "ORANGE",
            "PEN", "QUEEN", "RABBIT", "SUN", "TREE", "UMBRELLA", "VAN",
            "WATERMELON", "XYLOPHONE", "YAK", "ZEBRA"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alpha);

        // Set up click listeners for all letter containers
        setupClickListeners();
    }

    private void setupClickListeners() {
        // Get the container that holds all letter items
        LinearLayout questionsContainer = findViewById(R.id.questionsContainer);

        // Set click listeners for each letter container
        for (int i = 0; i < 26; i++) {
            final int index = i;
            LinearLayout letterContainer = (LinearLayout) questionsContainer.getChildAt(i);

            letterContainer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Show toast with letter and word information
                    String message = LETTERS[index] + " is for " + WORDS[index];
                    Toast.makeText(Alpha.this, message, Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}