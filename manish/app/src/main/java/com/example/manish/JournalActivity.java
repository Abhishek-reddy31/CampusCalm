package com.example.manish;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.app.AlertDialog.Builder;
import android.util.Log;  // Import Log for debugging
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class JournalActivity extends AppCompatActivity {

    private DatabaseReference journalReference;
    private String rollNumber;
    private EditText editJournalEntry; // Declare editJournalEntry as a class-level variable

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_journal);

        // Initialize Firebase
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        journalReference = database.getReference("journal_entries");

        // Assuming you retrieve the rollNumber from somewhere (e.g., SharedPreferences or Intent)
        rollNumber = getIntent().getStringExtra("user_rollNumber");
        Log.d("JournalActivity", "Roll Number: " + rollNumber); // Logging roll number

        String journalData = getIntent().getStringExtra("data");

        // Initialize editJournalEntry
        editJournalEntry = findViewById(R.id.editJournalEntry);
        editJournalEntry.setText(journalData);

        Button buttonSave = findViewById(R.id.buttonSave);
        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveJournalEntry();
            }
        });

        Button buttonViewAll = findViewById(R.id.buttonViewAll);
        buttonViewAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(JournalActivity.this, ViewAllEntriesActivity.class);
                intent.putExtra("user_rollNumber", rollNumber);
                startActivity(intent);
            }
        });
    }

    private void saveJournalEntry() {
        String journalData = getIntent().getStringExtra("data");
        String entry = editJournalEntry.getText().toString().trim();

        if (!entry.isEmpty()) {
            // Get the current date and time
            String currentDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
            String currentTime = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date());

            // Create a unique key for each journal entry using the current date and time
            String entryKey = currentDate + "_" + currentTime;
            Log.d("JournalActivity", "Entry key: " + entryKey); // Logging entry key

            // Create a JournalEntry object
            JournalEntry journalEntry = new JournalEntry(entry, currentDate, currentTime);

            // Save the entry to Firebase under the user's roll number
            journalReference.child(rollNumber).child(entryKey).setValue(journalEntry)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            showMessage("Success", "Journal entry added successfully!");
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            showMessage("Error", "Failed to add journal entry: " + e.getMessage());
                        }
                    });
        } else {
            showMessage("Error", "Journal entry cannot be empty!");
        }
    }

    public void showMessage(String title, String message) {
        Builder builder = new Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }
}
