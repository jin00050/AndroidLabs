package com.example.admin.androidlabs;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class LoginActivity extends Activity {

    protected static final String ACTIVITY_NAME = "LoginActivity";
    Button abt;
    SharedPreferences prefs;
    EditText email;
    String emailName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Log.i(ACTIVITY_NAME, "In onCreate()");

        email = (EditText)findViewById(R.id.EditText) ;
        prefs = getPreferences(Context.MODE_PRIVATE);
        emailName = prefs.getString("DefaultEmail", "email@domain.com");
        email.setText(emailName);

        Button abt = (Button) findViewById(R.id.button);
        abt.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {
                SharedPreferences.Editor edit = prefs.edit();
                String input =  email.getText().toString();
                edit.putString("DefaultEmail", input);
                edit.commit();

                Intent startIntent = new Intent(LoginActivity.this, StartActivity .class);
               // startIntent.putExtra("com.talkingandroid.MESSAGE", "Hello SecondaryActivity");
                startActivity(startIntent);
            }
        });
    }
    protected void onResume() {
        super.onResume();
        Log.i(ACTIVITY_NAME, "In onResume()");
    }
    protected void onStart() {
        super.onStart();
        Log.i(ACTIVITY_NAME, "In onStart()");
    }
    protected void onPause() {
        super.onPause();
        Log.i(ACTIVITY_NAME, "In onPause()");
    }
    protected void onStop() {
        super.onStop();
        Log.i(ACTIVITY_NAME, "In onStop()");
    }
    protected void onDestroy() {
        super.onDestroy();
        Log.i(ACTIVITY_NAME, "In onDestroy()");
    }
    public void callback(){

    }
}
