package com.raychal.uasmobile.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import com.raychal.uasmobile.databinding.ActivityMainBinding;
import com.raychal.uasmobile.db.SqliteHelper;
import com.raychal.uasmobile.util.SessionManager;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private ActivityMainBinding binding;
    private SqliteHelper db;
    private SessionManager sessionManager;
    private String username, email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        db = new SqliteHelper(this);
        this.setTitle("Halaman Utama");

        sessionManager = new SessionManager(getApplicationContext());

        username = sessionManager.getUserDetail().get(sessionManager.USERNAME);
        email = sessionManager.getUserDetail().get(sessionManager.EMAIL);

        binding.tvUsername.setText(username);
        binding.tvEmail.setText(email);

        boolean checkSession = db.checkSession("kosong");
        if (checkSession) {
            Intent loginIntent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(loginIntent);
            finish();
        }

        binding.logout.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        boolean updateSession = db.upgradeSession("ada", 1);
        if (updateSession) {
            Toast.makeText(getApplicationContext(), "Berhasil Keluar", Toast.LENGTH_SHORT).show();
            Intent loginIntent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(loginIntent);
            finish();
        }
    }
}