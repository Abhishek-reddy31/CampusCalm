package com.example.manish;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class RegisterScreen extends AppCompatActivity {

    private EditText rollNumberEditText;
    private EditText branchEditText;
    private EditText passwordEditText;
    private EditText confirmPasswordEditText;
    private EditText emailEditText;
    private EditText phoneNumberEditText;
    private DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_screen);

        // Initialize EditText fields
        rollNumberEditText = findViewById(R.id.Rollno);
        branchEditText = findViewById(R.id.Branch);
        passwordEditText = findViewById(R.id.password);
        confirmPasswordEditText = findViewById(R.id.confirmPassword);
        emailEditText = findViewById(R.id.email);
        phoneNumberEditText = findViewById(R.id.phno);

        // Initialize Firebase
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        reference = database.getReference("students");

        // Set click listener for the Register button
        Button registerButton = findViewById(R.id.registerButton);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Perform validation
                if (validateFields()) {
                    // Registration successful, save user data
                    checkExistingUserData();
                }
            }
        });
    }

    private boolean validateFields() {
        // Retrieve the text from EditText fields
        String rollNumber = rollNumberEditText.getText().toString().trim();
        String branch = branchEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();
        String confirmPassword = confirmPasswordEditText.getText().toString().trim();
        String email = emailEditText.getText().toString().trim();
        String phoneNumber = phoneNumberEditText.getText().toString().trim();

        // Perform validation logic
        if (rollNumber.isEmpty() || branch.isEmpty() || password.isEmpty() || confirmPassword.isEmpty() || email.isEmpty() || phoneNumber.isEmpty()) {
            // Display an error message if any field is empty
            Toast.makeText(this, "All fields are required", Toast.LENGTH_SHORT).show();
            return false;
        } else if (!password.equals(confirmPassword)) {
            // Display an error message if passwords do not match
            Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show();
            return false;
        } else if (!isValidEmail(email)) {
            // Display an error message if the email is not valid
            Toast.makeText(this, "Invalid email address", Toast.LENGTH_SHORT).show();
            return false;
        } else if (!isValidPhoneNumber(phoneNumber)) {
            // Display an error message if the phone number is not valid
            Toast.makeText(this, "Invalid phone number", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private boolean isValidEmail(CharSequence target) {
        // Perform a simple email validation
        return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }

    private boolean isValidPhoneNumber(String phoneNumber) {
        // Check if the phone number contains only numeric characters
        // and meets a reasonable length criterion
        return phoneNumber.matches("\\d{10}"); // Assumes a 10-digit phone number
    }

    // Method to redirect to the login page
    public void redirectToLoginF() {
        Intent intent = new Intent(this, LoginScreen.class);
        startActivity(intent);
        finish(); // Optional: Finish the current activity if you don't want to go back to it from the login page
    }

    public void redirectToLogin(View view){
        Intent intent = new Intent(this, LoginScreen.class);
        startActivity(intent);
        finish();
    }

    private void checkExistingUserData() {
        String rollNumber = rollNumberEditText.getText().toString().trim();
        String email = emailEditText.getText().toString().trim();
        String phoneNumber = phoneNumberEditText.getText().toString().trim();

        // Check if the roll number already exists
        reference.child(rollNumber).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    // Roll number already exists
                    Toast.makeText(RegisterScreen.this, "Roll number already exists. Choose a different one.", Toast.LENGTH_SHORT).show();
                } else {
                    // Continue checking for email and phone number
                    checkEmailAndPhoneNumber(rollNumber, email, phoneNumber);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle errors if needed
            }
        });
    }

    private void checkEmailAndPhoneNumber(String rollNumber, String email, String phoneNumber) {
        // Check if the email already exists
        Query emailQuery = reference.orderByChild("email").equalTo(email);
        emailQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    // Email already exists
                    Toast.makeText(RegisterScreen.this, "Email already registered. Use a different email.", Toast.LENGTH_SHORT).show();
                } else {
                    // Continue checking for phone number
                    checkPhoneNumber(rollNumber, email, phoneNumber);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle errors if needed
            }
        });
    }

    private void checkPhoneNumber(String rollNumber, String email, String phoneNumber) {
        // Check if the phone number already exists
        Query phoneQuery = reference.orderByChild("phoneNumber").equalTo(phoneNumber);
        phoneQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    // Phone number already exists
                    Toast.makeText(RegisterScreen.this, "Phone number already registered. Use a different number.", Toast.LENGTH_SHORT).show();
                } else {
                    // All checks passed, save user data
                    saveUserData(rollNumber, email, phoneNumber);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle errors if needed
            }
        });
    }

    private void saveUserData(String rollNumber, String email, String phoneNumber) {
        // Save user data to Firebase Realtime Database
        String password = passwordEditText.getText().toString().trim();
        String branch = branchEditText.getText().toString().trim(); // Add this line to get the branch
        User user = new User(rollNumber, branch, password, email, phoneNumber);

        reference.child(rollNumber).setValue(user)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(RegisterScreen.this, "Registration successful!", Toast.LENGTH_SHORT).show();
                        redirectToLoginF();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(RegisterScreen.this, "Failed to save user data: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

}
