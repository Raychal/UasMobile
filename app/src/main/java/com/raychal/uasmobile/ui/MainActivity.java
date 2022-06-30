package com.raychal.uasmobile.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import com.raychal.uasmobile.databinding.ActivityMainBinding;
import com.raychal.uasmobile.util.SessionManager;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private ActivityMainBinding binding;
    private SessionManager sessionManager;
    private String username, email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        sessionManager = new SessionManager(getApplicationContext());

        if (!sessionManager.isLoggedIn()) {
            Intent loginIntent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(loginIntent);
            finish();
        }

        username = sessionManager.getUserDetail().get(SessionManager.USERNAME);
        email = sessionManager.getUserDetail().get(SessionManager.EMAIL);

        binding.tvUsername.setText(username);
        binding.tvEmail.setText(email);

        binding.logout.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        sessionManager.logoutSession();
        Toast.makeText(getApplicationContext(), "Berhasil Keluar", Toast.LENGTH_SHORT).show();
        Intent loginIntent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(loginIntent);
        finish();
    }
}