package com.belatrix.pickmeup.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.belatrix.pickmeup.R;
import com.belatrix.pickmeup.model.FirebaseResponse;
import com.belatrix.pickmeup.model.MyUser;
import com.belatrix.pickmeup.rest.PickMeUpFirebaseClient;
import com.belatrix.pickmeup.rest.ServiceGenerator;
import com.belatrix.pickmeup.util.SharedPreferenceManager;
import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileActivity extends AppCompatActivity {

    private static final String TAG = "ProfileActivity";

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

    private String uid = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        Gson gson = new Gson();
        Intent intent = getIntent();
        MyUser currentUser = gson.fromJson(intent.getStringExtra("userJson"), MyUser.class);

        // Controls
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

        // Get User profile
        if(currentUser==null) {
            currentUser = SharedPreferenceManager.readMyUser(this);
        }
        this.uid = currentUser.getId();

        inputFirstName.setText(currentUser.getFirst_name());
        inputLastName.setText(currentUser.getLast_name());
        inputEmail.setText(currentUser.getEmail());
        inputSkypeId.setText(currentUser.getSkype_id());
        inputCellphone.setText(String.valueOf(currentUser.getCellphone()));

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateProfile(v);
            }
        });
    }

    public void updateProfile(View view) {
        final ProgressDialog progressDialog = new ProgressDialog(ProfileActivity.this,
                R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Actualizado...");
        progressDialog.show();

        String firstName = inputFirstName.getText().toString();
        String lastName = inputLastName.getText().toString();
        String email = inputEmail.getText().toString();
        String skypeId = inputSkypeId.getText().toString();
        String cellphone = inputCellphone.getText().toString();
        String password = inputPassword.getText().toString();

        // TODO: Validate data
        btnSignUp.setEnabled(true);
        MyUser user = new MyUser(Integer.parseInt(cellphone), email, firstName, lastName, skypeId);
        progressDialog.dismiss();
        updateAccount(uid, user);
    }

    private void updateAccount(String uid,final MyUser user) {
        // TODO: Fix error Invalid path: Invalid token in path
        Call<FirebaseResponse> call = ServiceGenerator.createService(PickMeUpFirebaseClient.class)
                .registerUser(user.getEmail(), user);
        call.enqueue(new Callback<FirebaseResponse>() {
            @Override
            public void onResponse(Call<FirebaseResponse> call, Response<FirebaseResponse> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(ProfileActivity.this, "User profile has been successfully updated",
                            Toast.LENGTH_SHORT).show();
                    onUpdateProfileSuccess();
                } else {
                    String failedMessage = response.errorBody().source().toString();
                    Log.e(TAG, failedMessage);
                }
            }

            @Override
            public void onFailure(Call<FirebaseResponse> call, Throwable t) {
                onUpdateProfileFailed();
            }
        });

    }

    public void onUpdateProfileSuccess() {
        finish();
    }

    public void onUpdateProfileFailed() {
        Toast.makeText(getApplicationContext(), "Update profile failed", Toast.LENGTH_SHORT).show();
    }
}
