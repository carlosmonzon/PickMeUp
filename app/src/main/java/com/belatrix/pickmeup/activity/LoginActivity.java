package com.belatrix.pickmeup.activity;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Patterns;
import android.widget.Button;
import android.widget.CheckBox;
import android.view.View;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.app.ProgressDialog;

import com.belatrix.pickmeup.R;

/**
 * Created by root on 13/05/16.
 */
public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "LoginActivity";

    private static final int REQUEST_SIGN_UP = 0;

    private Button btnLogin;

    private TextInputEditText inputUsername;

    private TextInputEditText inputPassword;

    private TextView textForgotUserPass;

    private TextView textSingIn;

    private TextInputLayout tilUsername;

    private TextInputLayout tilPassword;
    private CheckBox chRemember;

    private int counter = 3;

    private boolean authenticated = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        btnLogin = (Button) findViewById(R.id.login_btn);
        inputUsername = (TextInputEditText) findViewById(R.id.username_tiet);
        inputPassword = (TextInputEditText) findViewById(R.id.password_tiet);
        textForgotUserPass = (TextView) findViewById(R.id.forgot_username_password_link);
        textSingIn = (TextView) findViewById(R.id.sign_up_link);
        tilUsername = (TextInputLayout) findViewById(R.id.username_til);
        tilPassword = (TextInputLayout) findViewById(R.id.password_til);
        chRemember = (CheckBox) findViewById(R.id.checkBoxRemember);
        readCredentials();


        btnLogin.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                login(v);
            }
        });

        textForgotUserPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToForgotUsernamePassword(v);
            }
        });

        textSingIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToSignIn(v);
            }
        });
    }

    public void login(View view) {
        //Log.d(TAG, "Login");
        if (!validateLogin()) {
            onLoginFailed();
            return;
        }
        btnLogin.setEnabled(false);

        final ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this,
                R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Authenticating...");
        progressDialog.show();

        String username = inputUsername.getText().toString();
        String password = inputPassword.getText().toString();

        //Todo: Call service for authentication and Authorization
        if (username.equals("admin@pickmeup.com") &&
                password.equals("admin")) {
            authenticated = true;
        } else {
            authenticated = false;
            counter--;

            if (counter == 0) {
                btnLogin.setEnabled(false);
            }
        }

        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        // On complete call either onLoginSuccess or onLoginFailed
                        if (authenticated) {
                            onLoginSuccess();
                        } else {
                            onLoginFailed();
                        }
                        progressDialog.dismiss();
                    }
                }, 3000);

        if(authenticated){
            checkPreferences();
            goToHomeActivity(view);
        }
    }

    @Override
    public void onBackPressed() {
        // disable going back to the MainActivity
        moveTaskToBack(true);
    }

    public void onLoginSuccess() {
        btnLogin.setEnabled(true);
        finish();
    }

    public void onLoginFailed() {
        Toast.makeText(getApplicationContext(), "Login failed", Toast.LENGTH_SHORT).show();
        btnLogin.setEnabled(true);
    }

    public void goToHomeActivity(View view) {
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
    }

    public void goToForgotUsernamePassword(View view){
        Intent intent = new Intent(this, ForgotPasswordActivity.class);
        startActivity(intent);
    }

    public void goToSignIn(View view) {
        //Todo: Go to Sign In
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
    }

    public boolean validateLogin() {
        boolean valid = true;

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
        String username = inputUsername.getText().toString();
        String password = inputPassword.getText().toString();
        Boolean remember = chRemember.isChecked();

        if (remember){
            saveCredentials(username, password, remember);
        }else{
            saveCredentials(null,null,false);
        }
    }

    public void saveCredentials(String username,String password, Boolean remember){
        SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("username", username);
        editor.putString("password", password);
        editor.putBoolean("remember", remember);
        editor.commit();
    }

    public void readCredentials(){
        SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
        String username = sharedPref.getString("username", null);
        String password = sharedPref.getString("password", null);
        Boolean remember = sharedPref.getBoolean("remember", false);

        inputUsername.setText(username);
        inputPassword.setText(password);
        chRemember.setChecked(remember);
    }

}
