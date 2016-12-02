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
import com.belatrix.pickmeup.model.MyUserCredentials;
import com.belatrix.pickmeup.rest.PickMeUpFirebaseClient;
import com.belatrix.pickmeup.rest.ServiceGenerator;
import com.belatrix.pickmeup.util.RegularExpressionValidator;
import com.belatrix.pickmeup.util.SharedPreferenceManager;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseUser;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Created by root on 13/05/16.
 */
public class LoginActivity extends AppCompatActivity {

    private int passwordCounter = 3;
    private int noUserCounter = 3;

    private static final String TAG = "LoginActivity";

    private Button btnLogin;

    private TextInputEditText inputUsername;

    private TextInputEditText inputPassword;

    private TextView textForgotUserPass;

    private TextView textSingIn;

    private TextInputLayout tilUsername;

    private TextInputLayout tilPassword;

    private CheckBox chIsChecked;

    private boolean authenticated = false;

    private String failedMessage = "Complete Fields";

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
        MyUserCredentials user = SharedPreferenceManager.readMyUserCredentials(this);

        try {
            inputUsername.setText(user.getEmail());
            inputPassword.setText(user.getPassword());
            chIsChecked.setChecked(user.getChecked());
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
                goToSignUpActivity(nextView);
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

        ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this,
                R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Authenticating...");
        progressDialog.show();

        String username = inputUsername.getText().toString();
        String password = inputPassword.getText().toString();
        boolean isChecked = chIsChecked.isChecked();

        signInWithEmailAndPassword(progressDialog, username, password, isChecked);

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

    public void signInWithEmailAndPassword(final ProgressDialog progressDialog, final String username, final String password, final boolean isChecked) {

        mAuth.signInWithEmailAndPassword(username, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "signInWithEmail:onComplete:" + task.isSuccessful());

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "signInWithEmail:failed", task.getException());
                            progressDialog.dismiss();

                            if(task.getException().getClass().equals(FirebaseAuthInvalidUserException.class))
                                noUserCounter--;
                            else if (task.getException().getClass().equals(FirebaseAuthInvalidCredentialsException.class)) {
                                passwordCounter--;
                            }

                            if(task.getException().getClass().equals(FirebaseTooManyRequestsException.class)) {
                                Toast.makeText(LoginActivity.this, "Try again later",
                                        Toast.LENGTH_SHORT).show();
                            } else {
                                errorCounterHandler(noUserCounter, passwordCounter);
                            }


                        } else {
                            PickMeUpFirebaseClient client = ServiceGenerator.createServiceDeserializer(PickMeUpFirebaseClient.class);

                            Call<MyUser> callMyUsers = client.getUser("\"" + task.getResult().getUser().getUid() + "\"", "\"$key\"");

                            callMyUsers.enqueue(new Callback<MyUser>() {
                                @Override
                                public void onResponse(Call<MyUser> call, Response<MyUser> response) {

                                    if (response.code() == 200 && response.body().getEmail() != null) {
                                        authenticated = true;
                                        MyUser user = response.body();
                                        SharedPreferenceManager.saveMyUser(LoginActivity.this, user);
                                        if (isChecked) {
                                            SharedPreferenceManager.saveMyUserCredentials(LoginActivity.this, username, password, isChecked);
                                        } else {
                                            SharedPreferenceManager.saveMyUserCredentials(LoginActivity.this, null, null, false);
                                        }
                                    } else {
                                        authenticated = false;
                                        failedMessage = response.body().getEmail() != null?response.errorBody()
                                                .source().toString():"Login Error, user should register again";
                                        Log.e("Login", failedMessage);
                                    }

                                    if (authenticated) {
                                        progressDialog.dismiss();
                                        finish();
                                        goToHomeActivity(nextView);
                                    } else if (response.body().getEmail() == null) {
                                        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                                        user.delete()
                                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {
                                                        if (task.isSuccessful()) {
                                                            Log.d(TAG, "User account deleted.");
                                                            Toast.makeText(LoginActivity.this, "User doesn't exists, must create a new account",
                                                                    Toast.LENGTH_SHORT).show();
                                                        }
                                                    }
                                                });
                                        goToSignUpActivity(nextView);
                                    } else {
                                        onLoginFailed();
                                    }

                                }

                                @Override
                                public void onFailure(Call<MyUser> call, Throwable t) {
                                    Log.e("Login", failedMessage);
                                    failedMessage = failedMessage.substring(1, failedMessage.length() - 1).split("=")[1];
                                    Log.e("SPManager.failure:", t.toString());
                                    goToSignUpActivity(nextView);
                                }
                            });


                        }
                    }
                });
    }

    public void onLoginFailed() {
        Toast.makeText(getApplicationContext(), failedMessage, Toast.LENGTH_SHORT).show();
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

    public void errorCounterHandler(int noUserCounter, int invalidPasswordCounter)
    {
        if (invalidPasswordCounter == 0) {
            btnLogin.setEnabled(false);
            Toast.makeText(LoginActivity.this, "Recover your Password",
                    Toast.LENGTH_SHORT).show();
            goToForgotUsernamePassword(nextView);
        } else if (noUserCounter == 0) {
            btnLogin.setEnabled(false);
            Toast.makeText(LoginActivity.this, "Create a new User",
                    Toast.LENGTH_SHORT).show();
            goToSignUpActivity(nextView);        }
        else {
            Toast.makeText(LoginActivity.this, "Incorrect user or password",
                    Toast.LENGTH_SHORT).show();
        }
    }

}
