package com.example.practice4;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText mEditTextUser;
    private EditText mEditTextPassword;
    private Button mLoginButton;
    private Button mSignUpButton;

    private FirebaseAuth mFirebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mEditTextUser = findViewById(R.id.edit_text_user);
        mEditTextPassword = findViewById(R.id.edit_text_password);
        mLoginButton = findViewById(R.id.button_sign_in);
        mSignUpButton = findViewById(R.id.button_sign_up);

        mLoginButton.setOnClickListener(this);
        mSignUpButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        /** Implement the integration with Firebase Authentication */
        String email = mEditTextUser.getText().toString();
        String password = mEditTextPassword.getText().toString();

        /** Create an instance of the Firebase Authentication component */
        mFirebaseAuth = FirebaseAuth.getInstance();

        /** Check if the user email and password are valid */
        if (TextUtils.isEmpty(email)) {
            mEditTextUser.setHint("An email is required.");
            mEditTextUser.setHintTextColor(Color.RED);
            return;
        }
        if (TextUtils.isEmpty(password)) {
            mEditTextPassword.setHint("Please type the correct password.");
            mEditTextPassword.setHintTextColor(Color.RED);
            return;
        }

        /** If the user's input have the correct information, we need to invoke the
         * signInWithEmailAndPassword method.*/

        switch (v.getId()){
            case R.id.button_sign_in: SignIn(email, password); break;
            case R.id.button_sign_up: SignUp(email, password); break;
        }
    }

    private void SignIn(String email, String password){
        mFirebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            intent.putExtra("email", email);
                            startActivity(intent);
                            finish();
                        } else {
                            Toast.makeText(LoginActivity.this, "Authentication failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void SignUp(String email, String password){
        mFirebaseAuth.createUserWithEmailAndPassword(email, password).
                addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            FirebaseUser user = mFirebaseAuth.getCurrentUser();
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            intent.putExtra("email", user.getEmail());
                            startActivity(intent);
                            finish();
                        }
                    }
                });
    }
}