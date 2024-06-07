package com.example.finalprojectappointmentsystem;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class SignUp extends AppCompatActivity {

    private EditText etFullName, etEmail, etPassword, etPhoneNumber;
    private Button btnSignUp2;
    private TextView tvMessage;
    private DatabaseHelper databaseHelper;
    private Button btnSignIn2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.sign_up);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Initialize the views
        etFullName = findViewById(R.id.etFullName);
        etEmail = findViewById(R.id.intEmail);
        etPassword = findViewById(R.id.etPassword);
        etPhoneNumber = findViewById(R.id.intNumber);
        btnSignUp2 = findViewById(R.id.btnSignUp2);
        tvMessage = findViewById(R.id.tvMessage); // Assuming you have a TextView with this ID in your layout

        // Initialize the database helper
        databaseHelper = new DatabaseHelper(this);

        // Set click listener on the sign-up button
        btnSignUp2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validateInputs()) {
                    // Store user information in the database
                    String fullName = etFullName.getText().toString().trim();
                    String email = etEmail.getText().toString().trim();
                    String password = etPassword.getText().toString().trim();
                    String phoneNumber = etPhoneNumber.getText().toString().trim();

                    boolean isInserted = databaseHelper.insertUser(fullName, email, password, phoneNumber);
                    if (isInserted) {
                        displayMessage("Sign Up Successful");
                        Intent intent = new Intent(SignUp.this, Home.class);
                        intent.putExtra("FULL_NAME", fullName);
                        startActivity(intent);
                    } else {
                        displayMessage("Sign Up Failed");
                    }
                }
            }
        });

        btnSignIn2 = findViewById(R.id.btnSignIn2);

        btnSignIn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(SignUp.this, SignIn.class);
                startActivity(intent);
            }
        });
    }

    private boolean validateInputs() {
        // Validate full name
        if (TextUtils.isEmpty(etFullName.getText().toString().trim())) {
            etFullName.setError("Full Name is required");
            etFullName.requestFocus();
            return false;
        }

        // Validate email
        String email = etEmail.getText().toString().trim();
        if (TextUtils.isEmpty(email)) {
            etEmail.setError("Email is required");
            etEmail.requestFocus();
            return false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            etEmail.setError("Please enter a valid email");
            etEmail.requestFocus();
            return false;
        }

        // Validate password
        String password = etPassword.getText().toString().trim();
        if (TextUtils.isEmpty(password)) {
            etPassword.setError("Password is required");
            etPassword.requestFocus();
            return false;
        } else if (password.length() < 6) {
            etPassword.setError("Password must be at least 6 characters long");
            etPassword.requestFocus();
            return false;
        }

        // Validate phone number
        String phoneNumber = etPhoneNumber.getText().toString().trim();
        if (TextUtils.isEmpty(phoneNumber)) {
            etPhoneNumber.setError("Phone number is required");
            etPhoneNumber.requestFocus();
            return false;
        } else if (!Patterns.PHONE.matcher(phoneNumber).matches()) {
            etPhoneNumber.setError("Please enter a valid phone number");
            etPhoneNumber.requestFocus();
            return false;
        }

        return true;
    }

    private void displayMessage(String message) {
        tvMessage.setText(message);
        tvMessage.setVisibility(View.VISIBLE);
    }
}
