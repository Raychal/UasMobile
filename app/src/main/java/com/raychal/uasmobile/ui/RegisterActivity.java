package com.raychal.uasmobile.ui;

import static com.raychal.uasmobile.util.TextHtml.fromHtml;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import com.google.android.material.snackbar.Snackbar;
import com.raychal.uasmobile.databinding.ActivityRegisterBinding;
import com.raychal.uasmobile.db.SqliteHelper;
import com.raychal.uasmobile.model.User;

import java.util.Objects;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    private ActivityRegisterBinding binding;
    private SqliteHelper sqliteHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        sqliteHelper = new SqliteHelper(this);
        initTextViewLogin();
        binding.btnRegis.setOnClickListener(this);
    }

    public boolean validate() {
        boolean valid = false;

        String UserName = Objects.requireNonNull(binding.inputUsername.getText()).toString();
        String Email = Objects.requireNonNull(binding.inputEmail.getText()).toString();
        String Password = Objects.requireNonNull(binding.inputPassword.getText()).toString();

        if (UserName.isEmpty()) {
            binding.textInputLayoutUsername.setError("Please enter valid username!");
        } else {
            if (UserName.length() > 5) {
                valid = true;
                binding.textInputLayoutEmail.setError(null);
            } else {
                binding.textInputLayoutPassword.setError("Username is to short!");
            }
        }

        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(Email).matches()) {
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

    private void initTextViewLogin() {
        binding.tvLogin.setText(fromHtml("<font color='#0c0099'>back to login</font>"));
        binding.tvLogin.setOnClickListener(view -> finish());
    }

    @Override
    public void onClick(View v) {
        if (validate()) {
            String UserName = Objects.requireNonNull(binding.inputUsername.getText()).toString();
            String Email = Objects.requireNonNull(binding.inputEmail.getText()).toString();
            String Password = Objects.requireNonNull(binding.inputPassword.getText()).toString();

            if (!sqliteHelper.isUserNameExists(UserName)) {

                sqliteHelper.addUser(new User(null, UserName, Email, Password));
                Snackbar.make(binding.btnRegis, "User created successfully! Please Login ", Snackbar.LENGTH_LONG).show();
                new Handler().postDelayed(this::finish, Snackbar.LENGTH_LONG);
            }else {
                Snackbar.make(binding.btnRegis, "User already exists with same username ", Snackbar.LENGTH_LONG).show();
            }
        }
    }
}