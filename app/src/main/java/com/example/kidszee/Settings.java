package com.example.kidszee;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;

public class Settings extends AppCompatActivity {

    private SharedPreferences sharedPreferences;
    private Spinner spinnerLanguage;
    private Button btnWebsite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.Theme_Kidszee); // Apply Kidszee theme
        setContentView(R.layout.activity_settings);

        spinnerLanguage = findViewById(R.id.spinner_language);
        btnWebsite = findViewById(R.id.website);
        sharedPreferences = getSharedPreferences("Settings", MODE_PRIVATE);

        // 🧁 3-dot Menu
        ImageView menuButton = findViewById(R.id.menu_button);
        menuButton.setOnClickListener(v -> {
            PopupMenu popupMenu = new PopupMenu(Settings.this, menuButton);
            popupMenu.getMenuInflater().inflate(R.menu.settings_menu, popupMenu.getMenu());

            popupMenu.setOnMenuItemClickListener(item -> {
                int itemId = item.getItemId();

                if (itemId == R.id.menu_about) {
                    Toast.makeText(Settings.this, "About App clicked", Toast.LENGTH_SHORT).show();
                    return true;
                } else if (itemId == R.id.menu_contact) {
                    Toast.makeText(Settings.this, "Contact Us clicked", Toast.LENGTH_SHORT).show();
                    return true;
                } else if (itemId == R.id.menu_rate) {
                    Toast.makeText(Settings.this, "Rate Us clicked", Toast.LENGTH_SHORT).show();
                    return true;
                } else {
                    return false;
                }
            });


            popupMenu.show();
        });

        setupLanguageSpinner();
        setupWebsiteButton();
    }

    private void setupLanguageSpinner() {
        String[] languages = {"English", "Hindi", "Gujarati"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, languages);
        spinnerLanguage.setAdapter(adapter);

        String savedLanguage = sharedPreferences.getString("language", "en");
        switch (savedLanguage) {
            case "hi":
                spinnerLanguage.setSelection(1);
                break;
            case "gu":
                spinnerLanguage.setSelection(2);
                break;
            default:
                spinnerLanguage.setSelection(0);
                break;
        }

        spinnerLanguage.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String langCode = "en";
                if (position == 1) langCode = "hi";
                else if (position == 2) langCode = "gu";

                setLocale(langCode);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
    }

    private void setupWebsiteButton() {
        btnWebsite.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse("https://developer.android.com/studio"));
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        });
    }

    private void setLocale(String langCode) {
        String currentLang = sharedPreferences.getString("language", "en");
        if (!currentLang.equals(langCode)) {
            Locale locale = new Locale(langCode);
            Locale.setDefault(locale);
            Configuration config = new Configuration();
            config.setLocale(locale);
            getResources().updateConfiguration(config, getResources().getDisplayMetrics());

            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("language", langCode);
            editor.apply();

            recreate();
        }
    }
}
