package com.belatrix.pickmeup.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.view.View;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;
import android.graphics.Color;



import com.belatrix.pickmeup.R;

/**
 * Created by root on 13/05/16.
 */
public class LoginActivity extends AppCompatActivity {

    private Button btnLogin;
    private EditText txtUsername,txtPassword;

    private int counter = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        btnLogin=(Button)findViewById(R.id.loginButtom);
        txtUsername=(EditText)findViewById(R.id.usernameText);
        txtPassword=(EditText)findViewById(R.id.passwordText);

        btnLogin.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //Todo: Call service for authentication and Authorization
                if(txtUsername.getText().toString().equals("admin") &&
                        txtPassword.getText().toString().equals("admin")) {
                    Toast.makeText(getApplicationContext(), "Redirecting...",Toast.LENGTH_SHORT).show();
                    goToHomeActivity(v);
                }
                else{
                    Toast.makeText(getApplicationContext(), "Wrong Credentials",Toast.LENGTH_SHORT).show();

                    txtUsername.setVisibility(View.VISIBLE);
                    txtUsername.setBackgroundColor(Color.RED);
                    counter--;
                    txtUsername.setText(Integer.toString(counter));

                    if (counter == 0) {
                        btnLogin.setEnabled(false);
                    }
                }
            }
        });
    }

    public void goToHomeActivity(View view) {
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
    }

}
