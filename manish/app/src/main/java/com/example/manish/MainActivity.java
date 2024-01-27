package com.example.manish;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // to get data from loginscreen
        String branch = getIntent().getStringExtra("user_branch");
        String email = getIntent().getStringExtra("user_email");
        String rollNumber = getIntent().getStringExtra("user_rollNumber");
        String password = getIntent().getStringExtra("user_password");
        String phoneNumber = getIntent().getStringExtra("user_phoneNumber");
        // Find the profile button by its ID
        Button profileButton = findViewById(R.id.profileButton);

        // Set a click listener for the profile button
        profileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // When the button is clicked, redirect to YourActivity
                Intent intent = new Intent(MainActivity.this, YourActivity.class);
                intent.putExtra("user_branch", branch);
                intent.putExtra("user_email", email);
                intent.putExtra("user_rollNumber", rollNumber);
                intent.putExtra("user_password", password);
                intent.putExtra("user_phoneNumber", phoneNumber);
                startActivity(intent);
                finish();
            }
        });
        Button home = (Button) findViewById(R.id.homepage);
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent homeintent = new Intent(MainActivity.this,MainActivity.class);
                startActivity(homeintent);
                finish();
            }
        });

        Button journal = (Button) findViewById(R.id.Journal);
        journal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent journalintent = new Intent(MainActivity.this, JournalActivity.class);
                startActivity(journalintent);
            }
        });
        EditText journaldata = (EditText) findViewById(R.id.journaldata);

        Button saveJournal = (Button) findViewById(R.id.saveJournal);
        saveJournal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, JournalActivity.class);

                String data = journaldata.getText().toString().trim();
                intent.putExtra("data", data);
                intent.putExtra("user_rollNumber", rollNumber);
                startActivity(intent);
            }
        });
        Button Community =(Button) findViewById(R.id.Community);
        Community.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ChatActivity.class);
                startActivity(intent);
            }
        });
    }
}
