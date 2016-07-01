package com.belatrix.pickmeup.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.belatrix.pickmeup.R;

/**
 * Created by root on 13/05/16.
 */
public class SignUpActivity extends AppCompatActivity {

    private static final String TAG = "SignUpActivity";

    private Button btnSignUp;

    private TextInputEditText inputName;

    private TextInputEditText inputUsername;

    private TextInputEditText inputPassword;

    private TextView textGoBackToLogin;

    private TextInputLayout tilName;

    private TextInputLayout tilUsername;

    private TextInputLayout tilPassword;

    private int counter = 3;

    private boolean validData = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signUp);
        btnSignUp = (Button) findViewById(R.id.signUp_btn);
        inputName = (TextInputEditText) findViewById(R.id.signUp_name_tiet);
        inputUsername = (TextInputEditText) findViewById(R.id.signUp_username_tiet);
        inputPassword = (TextInputEditText) findViewById(R.id.signUp_password_tiet);
        textGoBackToLogin = (TextView) findViewById(R.id.sign_up_backToLogin_txt);
        tilName = (TextInputLayout) findViewById(R.id.signUp_name_til);
        tilUsername = (TextInputLayout) findViewById(R.id.signUp_username_til);
        tilPassword = (TextInputLayout) findViewById(R.id.signUp_password_til);
        readInfo();


        btnSignUp.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                signUp(v);
            }
        });

        textGoBackToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToLoginActivity(v);
            }
        });
    }

    public void signUp(View view) {
        if (!validateSignUp()) {
            onSignUpFailed();
            return;
        }
        btnSignUp.setEnabled(false);

        final ProgressDialog progressDialog = new ProgressDialog(SignUpActivity.this,
                R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Registering...");
        progressDialog.show();

        String name = inputName.getText().toString();
        String username = inputUsername.getText().toString();
        String password = inputPassword.getText().toString();

        //Todo: Call service for REGISTER new users
        if (name.length() > 3 && username.length() > 3 &&
                password.length() > 5) {
            validData = true;
        } else {
            validData = false;
            counter--;

            if (counter == 0) {
                btnSignUp.setEnabled(false);
            }
        }

        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        // On complete call either onLoginSuccess or onLoginFailed
                        if (validData) {
                            onSignUpSuccess();
                        } else {
                            onSignUpFailed();
                        }
                        progressDialog.dismiss();
                    }
                }, 3000);

        if(validData){
            checkPreferences();
            goToLoginActivity(view);
        }
    }

    @Override
    public void onBackPressed() {
        // disable going back to the MainActivity
        moveTaskToBack(true);
    }

    public void onSignUpSuccess() {
        btnSignUp.setEnabled(true);
        finish();
    }

    public void onSignUpFailed() {
        Toast.makeText(getApplicationContext(), "Sign Up failed", Toast.LENGTH_SHORT).show();
        btnSignUp.setEnabled(true);
    }

    public void goToLoginActivity(View view) {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    public boolean validateSignUp() {
        boolean valid = true;

        if (inputName.getText().toString().trim().equals("")) {
            tilName.setError(getResources().getString(R.string.name_empty_error));
            valid = false;
        }

        if (inputUsername.getText().toString().trim().equals("")) {
            tilUsername.setError(getResources().getString(R.string.username_empty_error));
            valid = false;
        }

        if (inputPassword.getText().toString().trim().equals("")) {
            tilPassword.setError(getResources().getString(R.string.password_empty_error));
            valid = false;
        }

        if (!isValidEmail(inputUsername.getText().toString().trim())) {
            tilUsername.setError(getResources().getString(R.string.username_invalid_error));
            valid = false;
        }

        return valid;

    }

    public final static boolean isValidEmail(CharSequence target) {
        return Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }

    public void checkPreferences(){
        String name = inputName.getText().toString();
        String username = inputUsername.getText().toString();
        String password = inputPassword.getText().toString();

        saveInfo(username, password, name);
    }

    public void saveInfo(String name, String username,String password){
        SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("name", name);
        editor.putString("username", username);
        editor.putString("password", password);
        editor.commit();
    }

    public void readInfo(){
        SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
        String name = sharedPref.getString("name", null);
        String username = sharedPref.getString("username", null);
        String password = sharedPref.getString("password", null);

        inputName.setText(name);
        inputUsername.setText(username);
        inputPassword.setText(password);
    }

}
