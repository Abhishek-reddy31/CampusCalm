package com.example.manish;
// UserManager.java

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class UserManager {

    public interface UserCheckListener {
        void onUserChecked(boolean userExists, boolean passwordMatches, String branch, String email, String rollNumber, String password, String phoneNumber);
    }

    public void checkUser(String userRollNumber, String userPassword, UserCheckListener listener) {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("students");
        Query checkUserDatabase = reference.orderByChild("rollNumber").equalTo(userRollNumber);

        checkUserDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    String passwordFromDB = snapshot.child(userRollNumber).child("password").getValue(String.class);
                    String branchFromDB = snapshot.child(userRollNumber).child("branch").getValue(String.class);
                    String emailFromDB = snapshot.child(userRollNumber).child("email").getValue(String.class);
                    String rollNumberFromDB = snapshot.child(userRollNumber).child("rollNumber").getValue(String.class);
                    String phoneNumberFromDB = snapshot.child(userRollNumber).child("phoneNumber").getValue(String.class);

                    assert passwordFromDB != null;
                    boolean passwordMatches = passwordFromDB.equals(userPassword);

                    listener.onUserChecked(true, passwordMatches, branchFromDB, emailFromDB, rollNumberFromDB, passwordFromDB, phoneNumberFromDB);
                } else {
                    listener.onUserChecked(false, false, null, null, null, null, null);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle errors if needed
            }
        });
    }
}
