package com.belatrix.pickmeup.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.belatrix.pickmeup.R;
import com.belatrix.pickmeup.util.RegularExpressionValidator;

public class ForgotPasswordActivity extends AppCompatActivity {

    private Button btnSendEmail;

    private TextInputEditText textEmail;

    private TextInputLayout tilEmail;

    private boolean sendSuccess = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        textEmail = (TextInputEditText) findViewById(R.id.email_tiet);
        tilEmail = (TextInputLayout) findViewById(R.id.email_til);
        btnSendEmail = (Button) findViewById(R.id.send_mail_btn);
    }

    public void sendEmail(View view) {
        if (!validateSendEmail()) {
            onSendFailed();
            return;
        }

        btnSendEmail.setEnabled(false);

        final ProgressDialog progressDialog = new ProgressDialog(ForgotPasswordActivity.this,
                R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Sending...");
        progressDialog.show();

        String email = textEmail.getText().toString();

        //Todo: Call service to send email
        sendSuccess = true;

        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        // On complete call either onLoginSuccess or onLoginFailed
                        if (sendSuccess) {
                            onSendSuccess();
                        } else {
                            onSendFailed();
                        }
                        progressDialog.dismiss();
                    }
                }, 3000);

        if (sendSuccess) {
            goToLoginActivity(view);
        }

    }

    public void onSendSuccess() {
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

    public void goToLoginActivity(View view) {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }
}
