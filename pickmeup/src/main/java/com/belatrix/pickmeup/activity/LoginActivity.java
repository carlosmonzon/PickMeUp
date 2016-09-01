package com.belatrix.pickmeup.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
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

import com.belatrix.pickmeup.model.Passenger;
import com.belatrix.pickmeup.rest.PickMeUpClient;
import com.belatrix.pickmeup.rest.ServiceGenerator;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import com.belatrix.pickmeup.model.Credentials;
import com.belatrix.pickmeup.util.RegularExpressionValidator;
import com.belatrix.pickmeup.util.SharedPreferenceManager;

import org.json.JSONObject;


/**
 * Created by root on 13/05/16.
 */
public class  LoginActivity extends AppCompatActivity {

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

    private String failedMessage = "Login Failed";

    private  SharedPreferences sharedPref;

    private Credentials credentials;

    private View nextView;

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
        sharedPref = getPreferences(Context.MODE_PRIVATE);
        credentials = SharedPreferenceManager.readCredentials(sharedPref);

        inputUsername.setText(credentials.getUsername());
        inputPassword.setText(credentials.getPassword());
        chRemember.setChecked(credentials.getRemember());

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
        nextView = view;
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

        credentials.setUsername(inputUsername.getText().toString());
        credentials.setPassword(inputPassword.getText().toString());
        credentials.setRemember(chRemember.isChecked());

        //Todo: Call service for authentication and Authorization
        PickMeUpClient client = ServiceGenerator.createService(PickMeUpClient.class);

        Call<Passenger> callPassengers = client.login(credentials);
        callPassengers.enqueue(new Callback<Passenger>() {
            @Override
            public void onResponse(Call<Passenger> call, Response<Passenger> response) {
                if(response.isSuccessful()) {
                    if (response.code() == 202){
                        Passenger passenger = response.body();
                        authenticated = true;
                        SharedPreferenceManager.checkPreferences(sharedPref, credentials);
                        SharedPreferenceManager.savePassenger(sharedPref, passenger);
                        Log.d("Passenger", passenger.getUserName());
                    }else{
                        authenticated = false;
                        counter--;

                        if (counter == 0) {
                            btnLogin.setEnabled(false);
                        }
                    }
                }else{
                    failedMessage = response.errorBody().source().toString();
                    Log.e("Login",failedMessage);
                    failedMessage = failedMessage.substring(1,failedMessage.length()-1).split("=")[1];
                    authenticated = false;
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
                    goToHomeActivity(nextView);
                }

            }

            @Override
            public void onFailure(Call<Passenger> call, Throwable t) {
                Log.e("Login",t.toString());
            }
        });

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
        Toast.makeText(getApplicationContext(), failedMessage, Toast.LENGTH_SHORT).show();
        btnLogin.setEnabled(true);
    }

    public void goToHomeActivity(View view) {
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
    }

    public void goToForgotUsernamePassword(View view) {
        Intent intent = new Intent(this, ForgotPasswordActivity.class);
        startActivity(intent);
    }

    public void goToSignIn(View view) {

        // Asynchronous Call in Retrofit 2.0

        PickMeUpClient client = ServiceGenerator.createService(PickMeUpClient.class);

        Call<List<Passenger>> callPassengers = client.getPassengers();

        callPassengers.enqueue(new Callback<List<Passenger>>() {
            @Override
            public void onResponse(Call<List<Passenger>> call, Response<List<Passenger>> response) {
                List<Passenger> passengers = response.body();
                for(Passenger passenger : passengers){
                    Log.d("Passenger", passenger.getUserName());
                }
            }

            @Override
            public void onFailure(Call<List<Passenger>> call, Throwable t) {
                Log.e("Get Passengers",t.toString());
            }
        });

        //Todo: Go to Sign In
        Intent intent = new Intent(this, SignUpActivity.class);
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

        if (!RegularExpressionValidator.isValidEmail(inputUsername.getText().toString().trim())) {
            tilUsername.setError(getResources().getString(R.string.username_invalid_error));
            valid = false;
        }

        return valid;

    }



}
