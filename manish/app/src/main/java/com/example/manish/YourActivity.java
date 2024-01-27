package com.example.manish;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class YourActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_your);

        // Retrieve user details from the Intent
        String branch = getIntent().getStringExtra("user_branch");
        String email = getIntent().getStringExtra("user_email");
        String rollNumber = getIntent().getStringExtra("user_rollNumber");
        String password = getIntent().getStringExtra("user_password");
        String phoneNumber = getIntent().getStringExtra("user_phoneNumber");

        // Find TextViews in the layout
        TextView textBranch = findViewById(R.id.textBranch);
        TextView textEmail = findViewById(R.id.textEmail);
        TextView textRollNumber = findViewById(R.id.textRollNumber);
        TextView textPassword = findViewById(R.id.textPassword);
        TextView textPhoneNumber = findViewById(R.id.textPhoneNumber);
        TextView ProfileName = findViewById(R.id.ProfileName);

        ProfileName.setText(rollNumber);

        // Set the retrieved values to the TextViews
        textBranch.setText("Branch: " + branch);
        textEmail.setText("Email: " + email);
        textRollNumber.setText("Roll Number: " + rollNumber);
        textPassword.setText("Password: " + password);
        textPhoneNumber.setText("Phone Number: " + phoneNumber);

        // Optionally, you can handle logout button click if needed
        Button buttonLogout = findViewById(R.id.buttonLogout);
        buttonLogout.setOnClickListener(view -> {
            // Perform logout actions if needed
            // For example, you can navigate back to the login screen
            Intent intent = new Intent(YourActivity.this, LoginScreen.class);
            startActivity(intent);
            finish(); // Close the current activity
        });

        Button backbutton = (Button) findViewById(R.id.backbutton);
        backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent back = new Intent(YourActivity.this, MainActivity.class);
                startActivity(back);
                finish();
            }
        });
    }

}
