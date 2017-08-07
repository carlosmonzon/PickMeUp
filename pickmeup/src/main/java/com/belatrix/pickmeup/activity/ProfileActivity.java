package com.belatrix.pickmeup.activity;

import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

import com.belatrix.pickmeup.R;
import com.belatrix.pickmeup.model.MyUser;
import com.belatrix.pickmeup.util.SharedPreferenceManager;
import com.google.gson.Gson;

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
        if(currentUser==null){
            currentUser = SharedPreferenceManager.readMyUser(this);
        }

        inputFirstName.setText(currentUser.getFirst_name());
        inputLastName.setText(currentUser.getLast_name());
        inputEmail.setText(currentUser.getSkype_id());
        // inputCellphone.setText(currentUser.getCellphone());
    }
    
}
