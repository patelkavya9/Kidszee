package com.example.kidszee;



import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class numbers extends AppCompatActivity {

    // Array of letter names and corresponding words
    private final String[] NUMBERS = {"1","2","3","4","5","6","7","8","9","10"};

    private final String[] WORDS = {"ONE", "TWO", "THREE", "FOUR", "FIVE", "SIX", "SEVEN", "EIGHT",
            "NINE", "TEN",};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_numbers);

        // Set up click listeners for all letter containers

    }

}
