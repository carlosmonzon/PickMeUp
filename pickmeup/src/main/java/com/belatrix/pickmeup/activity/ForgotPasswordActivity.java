package com.belatrix.pickmeup.activity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.belatrix.pickmeup.R;
import com.belatrix.pickmeup.util.RegularExpressionValidator;

public class ForgotPasswordActivity extends AppCompatActivity {

    private static final String TAG = "ForgotPasswordActivity";

    private Button btnSendEmail;

    private TextInputEditText textEmail;

    private TextInputLayout tilEmail;


    private FirebaseAuth mAuth;

    private FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_forgot_password);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        textEmail = (TextInputEditText) findViewById(R.id.email_tiet);
        tilEmail = (TextInputLayout) findViewById(R.id.email_til);
        btnSendEmail = (Button) findViewById(R.id.send_mail_btn);

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                } else {
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                }
            }
        };
    }

    public void sendEmail(View view) {
        if (!validateSendEmail()) {
            onSendFailed();
            return;
        }

        btnSendEmail.setEnabled(false);

        mAuth = FirebaseAuth.getInstance();

        final ProgressDialog progressDialog = new ProgressDialog(ForgotPasswordActivity.this,
                R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Sending...");
        progressDialog.show();

        String emailAddress = textEmail.getText().toString();

        mAuth.sendPasswordResetEmail(emailAddress)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "Email sent.");
                            progressDialog.dismiss();
                            onSendSuccess();
                        } else {
                            onSendFailed();
                        }
                    }
                });

    }

    public void onSendSuccess() {
        Toast.makeText(getApplicationContext(), "Mail has been sent", Toast.LENGTH_SHORT).show();
        btnSendEmail.setEnabled(true);
        finish();
    }

    public void onSendFailed() {
        Toast.makeText(getApplicationContext(), "Send failed", Toast.LENGTH_SHORT).show();
        btnSendEmail.setEnabled(true);
    }


    public boolean validateSendEmail() {
        boolean valid = true;

        tilEmail.setError(null);

        if (textEmail.getText().toString().trim().equals("")) {
            tilEmail.setError(getResources().getString(R.string.email_empty_error));
            valid = false;
        }

        if (!RegularExpressionValidator.isValidEmail(textEmail.getText().toString().trim())) {
            tilEmail.setError(getResources().getString(R.string.email_invalid_error));
            valid = false;
        }

        return valid;

    }

}
