package com.example.manish;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.app.AlertDialog.Builder;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ViewAllEntriesActivity extends AppCompatActivity {

    private DatabaseReference journalReference;
    private LinearLayout entriesContainer; // This will be a LinearLayout where entries will be added

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.entry_item_layout);

        // Initialize Firebase
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        journalReference = database.getReference("journal_entries");

        entriesContainer = findViewById(R.id.entriesContainer); // assuming you have a LinearLayout in your layout with this id

        // Retrieve entries for the specific user
        retrieveEntries();
    }

    private void retrieveEntries() {
        // Assuming you have the rollNumber from the previous activity
        String rollNumber = getIntent().getStringExtra("user_rollNumber");

        journalReference.child(rollNumber).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                entriesContainer.removeAllViews();
                if (dataSnapshot.exists()) {
                    // User has entries, iterate through them and add to the layout
                    for (DataSnapshot entrySnapshot : dataSnapshot.getChildren()) {
                        addEntryToLayout(entrySnapshot.getValue(JournalEntry.class));
                    }
                } else {
                    // User has no entries, show a message or handle accordingly
                    showMessage("No Entries", "You don't have any journal entries yet.");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle error
            }
        });
    }

    private void addEntryToLayout(JournalEntry entry) {
        // Inflate a layout for each entry
        View entryView = LayoutInflater.from(this).inflate(R.layout.entry_item_layout, null);

        // Set the text for date, time, and entry content
        TextView dateTextView = entryView.findViewById(R.id.dateTextView);
        dateTextView.setText("Date: " + entry.getDate());

        TextView timeTextView = entryView.findViewById(R.id.timeTextView);
        timeTextView.setText("Time: " + entry.getTime());

        TextView entryContentTextView = entryView.findViewById(R.id.entryContent);
        entryContentTextView.setText(entry.getEntry());

        // Add the inflated layout to the entriesContainer
        entriesContainer.addView(entryView);
    }

    public void showMessage(String title, String message) {
        Builder builder = new Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }

}
