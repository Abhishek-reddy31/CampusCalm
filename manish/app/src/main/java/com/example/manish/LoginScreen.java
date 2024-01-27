package com.example.manish;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class LoginScreen extends AppCompatActivity {

    EditText loginUsername, loginPassword;
    Button loginButton;
    TextView signupRedirectText;

    private UserManager userManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);

        userManager = new UserManager();

        loginUsername = findViewById(R.id.Rollno);
        loginPassword = findViewById(R.id.password);
        loginButton = findViewById(R.id.loginButton);
        signupRedirectText = findViewById(R.id.signupText);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validateCredentials()) {
                    checkUser();
                }
            }
        });

        signupRedirectText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginScreen.this, RegisterScreen.class);
                startActivity(intent);
            }
        });
    }

    private boolean validateCredentials() {
        String username = loginUsername.getText().toString().trim();
        String password = loginPassword.getText().toString().trim();

        if (TextUtils.isEmpty(username) || TextUtils.isEmpty(password)) {
            // Display an error message if any field is empty
            Toast.makeText(this, "Username and password are required", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    private void checkUser() {
        String userUsername = loginUsername.getText().toString().trim();
        String userPassword = loginPassword.getText().toString().trim();

        userManager.checkUser(userUsername, userPassword, new UserManager.UserCheckListener() {
            @Override
            public void onUserChecked(boolean userExists, boolean passwordMatches, String branch, String email, String rollNumber, String password, String phoneNumber) {
                if (userExists) {
                    if (passwordMatches) {
                        loginUsername.setError(null);

                        // Redirect to MainActivity upon successful login
                        Intent intent = new Intent(LoginScreen.this, MainActivity.class);
                        intent.putExtra("user_branch", branch);
                        intent.putExtra("user_email", email);
                        intent.putExtra("user_rollNumber", rollNumber);
                        intent.putExtra("user_password", password);
                        intent.putExtra("user_phoneNumber", phoneNumber);
                        startActivity(intent);
                        finish();
                    } else {
                        loginPassword.setError("Invalid Credentials");
                        loginPassword.requestFocus();
                    }
                } else {
                    loginUsername.setError("User does not exist");
                    loginUsername.requestFocus();
                }
            }
        });
    }
}
