package com.belatrix.pickmeup.activity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.belatrix.pickmeup.R;
import com.belatrix.pickmeup.model.MyUser;
import com.belatrix.pickmeup.model.Passenger;
import com.belatrix.pickmeup.rest.PickMeUpClient;
import com.belatrix.pickmeup.rest.PickMeUpFirebaseClient;
import com.belatrix.pickmeup.rest.ServiceGenerator;
import com.belatrix.pickmeup.util.RegularExpressionValidator;
import com.belatrix.pickmeup.util.SharedPreferenceManager;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


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

    private CheckBox chIsChecked;

    private int counter = 3;

    private boolean authenticated = false;

    private String failedMessage = "Login Failed";


    private View nextView;

    private FirebaseAuth.AuthStateListener mAuthListener;

    private FirebaseAuth mAuth;


    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();

        btnLogin = (Button) findViewById(R.id.login_btn);
        inputUsername = (TextInputEditText) findViewById(R.id.username_tiet);
        inputPassword = (TextInputEditText) findViewById(R.id.password_tiet);
        textForgotUserPass = (TextView) findViewById(R.id.forgot_username_password_link);
        textSingIn = (TextView) findViewById(R.id.sign_up_link);
        tilUsername = (TextInputLayout) findViewById(R.id.username_til);
        tilPassword = (TextInputLayout) findViewById(R.id.password_til);
        chIsChecked = (CheckBox) findViewById(R.id.checkBoxRemember);
        MyUser user = SharedPreferenceManager.readMyUser(this);

        try {
            inputUsername.setText(user.getEmail());
            inputPassword.setText(user.getPassword());
            chIsChecked.setChecked(user.getIsChecked());
        } catch (Exception e) {
            Log.d(TAG, e.getMessage());
        }

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

        FirebaseApp.initializeApp(this);
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                } else {
                    // User is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                }
                // ...
            }
        };
    }

    public void login(final View view) {
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


        mAuth.signInWithEmailAndPassword(inputUsername.getText().toString(), inputPassword.getText().toString())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "signInWithEmail:onComplete:" + task.isSuccessful());

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "signInWithEmail:failed", task.getException());
                            Toast.makeText(LoginActivity.this, "FAIL",
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            PickMeUpFirebaseClient client = ServiceGenerator.createServiceDeserializer(PickMeUpFirebaseClient.class);

                            Call<MyUser> callMyUsers = client.getUser("\""+task.getResult().getUser().getUid()+"\"", "\"$key\"");

                            callMyUsers.enqueue(new Callback<MyUser>() {
                                @Override
                                public void onResponse(Call<MyUser> call, Response<MyUser> response) {
                                    if (response.isSuccessful()) {
                                        if (response.code() == 200 && response.body()!=null) {
                                            authenticated = true;
                                            MyUser user = response.body();
                                            SharedPreferenceManager.saveMyUser(LoginActivity.this, user);
                                        } else {
                                            authenticated = false;
                                            counter--;

                                            if (counter == 0) {
                                                btnLogin.setEnabled(false);
                                            }
                                        }
                                    } else {
                                        failedMessage = response.errorBody().source().toString();
                                        Log.e("Login", failedMessage);
                                        failedMessage = failedMessage.substring(1, failedMessage.length() - 1).split("=")[1];
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

                                    if (authenticated) {
                                        goToHomeActivity(nextView);
                                    } else if(response.body()==null) {
                                        goToSignUpActivity(nextView);
                                    }
                                }

                                @Override
                                public void onFailure(Call<MyUser> call, Throwable t) {
                                    Log.e("SPManager.failure:", t.toString());
                                }
                            });



                        }


                    }
                });
    }

    @Override
    public void onBackPressed() {
        // disable going back to the MainActivity
        new AlertDialog.Builder(this)
                .setTitle("Logout")
                .setMessage("Would you like to logout?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // user doesn't want to logout
                    }
                })
                .show();
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

    public void goToSignUpActivity(View view) {
        Intent intent = new Intent(this, SignUpActivity.class);
        startActivity(intent);
    }

    public void goToSignIn(View view) {

        // Asynchronous Call in Retrofit 2.0

        PickMeUpClient client = ServiceGenerator.oldCreateService(PickMeUpClient.class);

        Call<List<Passenger>> callPassengers = client.getPassengers();

        callPassengers.enqueue(new Callback<List<Passenger>>() {
            @Override
            public void onResponse(Call<List<Passenger>> call, Response<List<Passenger>> response) {
                List<Passenger> passengers = response.body();
                for (Passenger passenger : passengers) {
                    Log.d("Passenger", passenger.getUserName());
                }
            }

            @Override
            public void onFailure(Call<List<Passenger>> call, Throwable t) {
                Log.e("Get Passengers", t.toString());
            }
        });

        goToSignUpActivity(view);
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
