package com.example.kidszee;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Login extends AppCompatActivity {

    private EditText etEmail, etPassword;
    private CheckBox keepLoggedIn;
    private Button btnLogin, btnRegister;
    private SQLiteDBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
        btnRegister = findViewById(R.id.btnRegister);
        keepLoggedIn = findViewById(R.id.checkBox);

        dbHelper = new SQLiteDBHelper(this);

        // Auto-login if user checked "Keep me logged in" (You might want to implement this differently with SQLite)
        // For simplicity, we'll skip auto-login logic here

        btnLogin.setOnClickListener(v -> loginUser());
        btnRegister.setOnClickListener(v -> startActivity(new Intent(Login.this, Registration.class)));
    }

    private void loginUser() {
        String email = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString().trim();

        if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Please enter email and password", Toast.LENGTH_SHORT).show();
            return;
        }

        if (dbHelper.loginUser(email, password)) {
            Toast.makeText(Login.this, "Login successful", Toast.LENGTH_SHORT).show();

            if (keepLoggedIn.isChecked()) {
                // You might want to save a flag in SharedPreferences or SQLite for auto-login
                // For simplicity, we'll skip this here
            }

            startActivity(new Intent(Login.this, MainActivity.class));
            finish();
        } else {
            Toast.makeText(Login.this, "Incorrect email or password", Toast.LENGTH_SHORT).show();
        }
    }
}
