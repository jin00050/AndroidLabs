package com.example.admin.androidlabs;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class StartActivity extends Activity {

    protected static final String ACTIVITY_NAME = "StartActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        Log.i(ACTIVITY_NAME, "In onCreate()");

        Button b1 = (Button) findViewById(R.id.button);
        b1.setOnClickListener(new View.OnClickListener() {
                                  @Override
                                  public void onClick(View view) {
                                      Intent intent = new Intent(StartActivity.this, ListItemsActivity.class);
                                      startActivityForResult(intent, 10);
                                  }
                              }
        );
        Button newButton= (Button) findViewById(R.id.newButton);
        newButton.setOnClickListener(new View.OnClickListener() {
                                  @Override
                                  public void onClick(View view) {
                                      Log.i(ACTIVITY_NAME, "User clicked Start Chat");
                                      Intent intent = new Intent(StartActivity.this, ChatWindowActivity.class);
                                      startActivityForResult(intent, 10);
                                  }
                              }
        );
        Button weatherButton= (Button) findViewById(R.id.weatherButton);
        weatherButton.setOnClickListener(new View.OnClickListener() {
                                         @Override
                                         public void onClick(View view) {
                                             Log.i(ACTIVITY_NAME, "User clicked To check The Information Of Weather");
                                             Intent intent = new Intent(StartActivity.this, WeatherForecast.class);
                                             startActivityForResult(intent, 10);
                                         }
                                     }
        );
        Button toolbarButton =(Button) findViewById(R.id.test_toolbar_Button);
        toolbarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i(ACTIVITY_NAME, "User clicked To test the toolbar");
                Intent intent = new Intent(StartActivity.this, TestToolbar .class);
                startActivityForResult(intent, 10);

            }

        });
    }
    public void  onActivityResult(int requestCode, int responseCode, Intent data){
        if(requestCode == 10){
        Log.i(ACTIVITY_NAME, "Returned to StartActivity.onActivityResult");}

        if(requestCode == Activity.RESULT_OK){

            String result = data.getStringExtra("Response");
            Toast toast = Toast.makeText(this , result, Toast.LENGTH_LONG);
            toast.show();

        }
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
}
