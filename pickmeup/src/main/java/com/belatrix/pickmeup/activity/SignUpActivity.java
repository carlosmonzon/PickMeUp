package com.belatrix.pickmeup.activity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.telephony.PhoneNumberUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.belatrix.pickmeup.R;
import com.belatrix.pickmeup.model.FirebaseResponse;
import com.belatrix.pickmeup.model.MyUser;
import com.belatrix.pickmeup.rest.PickMeUpFirebaseClient;
import com.belatrix.pickmeup.rest.ServiceGenerator;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by root on 13/05/16.
 */
public class SignUpActivity extends AppCompatActivity {

    private static final String TAG = "SignUpActivity";

    private Button btnSignUp;

    private TextInputEditText inputFirstName;

    private TextInputEditText inputLastName;

    private TextInputEditText inputEmail;

    private TextInputEditText inputCellphone;

    private TextInputEditText inputSkypeId;

    private TextInputEditText inputPassword;

    private TextInputEditText inputConfirmPassword;

    private TextInputLayout tilFirstName;

    private TextInputLayout tilLastName;

    private TextInputLayout tilEmail;

    private TextInputLayout tilCellphone;

    private TextInputLayout tilSkypeId;

    private TextInputLayout tilPassword;

    private TextInputLayout tilConfirmPassword;

    private FirebaseAuth.AuthStateListener mAuthListener;

    private FirebaseAuth mAuth;

    private int counter = 3;

    private boolean validData = false;

    public final static boolean isValidEmail(CharSequence target) {
        return Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }

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
        setContentView(R.layout.activity_signup);

        btnSignUp = (Button) findViewById(R.id.signup_btn);
        inputFirstName = (TextInputEditText) findViewById(R.id.signup_first_name_tiet);
        inputLastName = (TextInputEditText) findViewById(R.id.signup_last_name_tiet);
        inputEmail = (TextInputEditText) findViewById(R.id.signup_email_tiet);
        inputSkypeId = (TextInputEditText) findViewById(R.id.signup_skype_id_tiet);
        inputCellphone = (TextInputEditText) findViewById(R.id.signup_cellphone_tiet);
        inputPassword = (TextInputEditText) findViewById(R.id.signup_password_tiet);
        inputConfirmPassword = (TextInputEditText) findViewById(R.id.signup_confirm_password_tiet);
        tilFirstName = (TextInputLayout) findViewById(R.id.signup_first_name_til);
        tilLastName = (TextInputLayout) findViewById(R.id.signup_last_name_til);
        tilEmail = (TextInputLayout) findViewById(R.id.signup_email_til);
        tilSkypeId = (TextInputLayout) findViewById(R.id.signup_skype_id_til);
        tilCellphone = (TextInputLayout) findViewById(R.id.signup_cellphone_til);
        tilPassword = (TextInputLayout) findViewById(R.id.signup_password_til);
        tilConfirmPassword = (TextInputLayout) findViewById(R.id.signup_confirm_password_til);

        mAuth = FirebaseAuth.getInstance();

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

        btnSignUp.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                signup(v);
            }
        });
    }

    public void signup(View view) {
        if (!validateSignUp()) {
            onSignUpFailed();
            return;
        }

        final ProgressDialog progressDialog = new ProgressDialog(SignUpActivity.this,
                R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Registrando...");
        progressDialog.show();

        String firstName = inputFirstName.getText().toString();
        String lastName = inputLastName.getText().toString();
        String email = inputEmail.getText().toString();
        String skypeId = inputSkypeId.getText().toString();
        String cellphone = inputCellphone.getText().toString();
        String password = inputPassword.getText().toString();

        validData = true;
        btnSignUp.setEnabled(true);
        MyUser user = new MyUser(Integer.parseInt(cellphone), email, firstName, lastName, skypeId);
        progressDialog.dismiss();
        createAccount(email, password, user);
    }


    public void onSignUpSuccess() {
        btnSignUp.setEnabled(true);
        finish();
    }

    public void onSignUpFailed() {
        Toast.makeText(getApplicationContext(), "Sign Up failed", Toast.LENGTH_SHORT).show();
        counter--;
        if (counter == 0)
            btnSignUp.setEnabled(false);
        else
            btnSignUp.setEnabled(true);
    }

    public void onBackPressed() {
        finish();
    }

    public boolean validateSignUp() {
        boolean valid = true;

        if (inputFirstName.getText().toString().trim().isEmpty() ||
                !isAlpha(inputFirstName.getText().toString())) {
            tilFirstName.setError(getResources().getString(R.string.name_error));
            valid = false;
        }

        if (inputLastName.getText().toString().trim().isEmpty() ||
                !isAlpha(inputLastName.getText().toString())) {
            tilLastName.setError(getResources().getString(R.string.last_name_error));
            valid = false;
        }

        if (inputPassword.getText().toString().trim().isEmpty() ||
                inputPassword.getText().toString().length()<6) {
            tilPassword.setError(getResources().getString(R.string.password_empty_error));
            valid = false;
        }

        if (inputConfirmPassword.getText().toString().trim().isEmpty() ||
                inputConfirmPassword.getText().toString().length()<6) {
            tilConfirmPassword.setError(getResources().getString(R.string.password_empty_error));
            valid = false;
        }

        if (!PhoneNumberUtils.isGlobalPhoneNumber(inputCellphone.getText().toString()) ||
                inputCellphone.getText().toString().length()>10) {
            tilCellphone.setError(getResources().getString(R.string.cellphone_error));
            valid = false;
        }

        if (inputSkypeId.getText().toString().trim().isEmpty()) {
            tilSkypeId.setError(getResources().getString(R.string.skype_id_empty_error));
            valid = false;
        }

        if (!isValidEmail(inputEmail.getText().toString().trim())) {
            tilEmail.setError(getResources().getString(R.string.email_invalid_error));
            valid = false;
        }

        if(!inputConfirmPassword.getText().toString().trim().equals(inputPassword.getText().toString().trim())){
            tilConfirmPassword.setError(getResources().getString(R.string.password_invalid_match));
            valid = false;
        }
        return valid;

    }


    private void createAccount(String email, String password, final MyUser user) {
        Log.d(TAG, "createAccount:" + email);

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "createUserWithEmail:onComplete:" + task.isSuccessful());
                        if (!task.isSuccessful()) {
                            onSignUpFailed();
                        } else {
                            Call<FirebaseResponse> call = ServiceGenerator.createService(PickMeUpFirebaseClient.class)
                                    .registerUser(task.getResult().getUser().getUid(), user);
                            call.enqueue(new Callback<FirebaseResponse>() {
                                @Override
                                public void onResponse(Call<FirebaseResponse> call, Response<FirebaseResponse> response) {
                                    if (response.isSuccessful()) {
                                        Toast.makeText(SignUpActivity.this, "User has been successfully created",
                                                Toast.LENGTH_SHORT).show();
                                        onSignUpSuccess();

                                    } else {
                                        String failedMessage = response.errorBody().source().toString();
                                        Log.e("Login", failedMessage);
                                    }
                                }

                                @Override
                                public void onFailure(Call<FirebaseResponse> call, Throwable t) {
                                    onSignUpFailed();
                                }
                            });

                        }
                    }
                });
    }

    public boolean isAlpha(String s){
        String pattern= "^[\\p{L} .'-]+$";
        return s.matches(pattern);
    }

}
