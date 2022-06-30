package com.raychal.uasmobile.ui;

import static com.raychal.uasmobile.util.TextHtml.fromHtml;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.snackbar.Snackbar;
import com.raychal.uasmobile.databinding.ActivityLoginBinding;
import com.raychal.uasmobile.db.SqliteHelper;
import com.raychal.uasmobile.model.User;
import com.raychal.uasmobile.util.SessionManager;

import java.util.Objects;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private ActivityLoginBinding binding;
    private SqliteHelper sqliteHelper;
    private SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        sqliteHelper = new SqliteHelper(this);
        initCreateAccountTextView();
        binding.btnLogin.setOnClickListener(this);

        sessionManager = new SessionManager(getApplicationContext());
    }

    private void initCreateAccountTextView() {
        binding.tvCreateAccount.setText(fromHtml("<font color='#FF000000'>I don't have account yet. </font><font color='#0c0099'>create one</font>"));
        binding.tvCreateAccount.setOnClickListener(view -> {
            Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivity(intent);
        });
    }

    @Override
    public void onClick(View v) {
        if (validate()) {
            String UserName = Objects.requireNonNull(binding.inputUsername.getText()).toString();
            String Password = Objects.requireNonNull(binding.inputPassword.getText()).toString();
            User currentUser = sqliteHelper.Authenticate(new User(null, UserName, null, Password));
            if (currentUser != null) {

                boolean updateSession = sqliteHelper.upgradeSession("ada", 1);
                if (updateSession){
                    Snackbar.make(binding.btnLogin, "Successfully Logged in!", Snackbar.LENGTH_LONG).show();

                    sessionManager.createLoginSession(currentUser);
                    sessionManager.getUserDetail();
                    Intent intent=new Intent(LoginActivity.this,MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            } else {
                Snackbar.make(binding.btnLogin, "Failed to log in , please try again", Snackbar.LENGTH_LONG).show();
            }
        }
    }

    private boolean validate() {
        boolean valid = false;

        String UserName = Objects.requireNonNull(binding.inputUsername.getText()).toString();
        String Password = Objects.requireNonNull(binding.inputPassword.getText()).toString();

        if (UserName.isEmpty()) {
            binding.textInputLayoutEmail.setError("Please enter valid email!");
        } else {
            valid = true;
            binding.textInputLayoutEmail.setError(null);
        }

        if (Password.isEmpty()) {
            binding.textInputLayoutPassword.setError("Please enter valid password!");
        } else {
            if (Password.length() > 5) {
                valid = true;
                binding.textInputLayoutPassword.setError(null);
            } else {
                binding.textInputLayoutPassword.setError("Password is to short!");
            }
        }
        return valid;
    }
}