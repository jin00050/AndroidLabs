package com.example.admin.androidlabs;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MessageDetails extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_details);
//        open bundle_phone
        Bundle bundle = getIntent().getBundleExtra("bundle_phone");
//        load bundle into the new Messagefragment
        MessageFragment fragment_phone=new MessageFragment();
        fragment_phone.setArguments(bundle);
//        load the new MessageFragment into the emptyFrame with ft
        FragmentTransaction ft= getFragmentManager().beginTransaction();
        ft.replace(R.id.emptyFrame , fragment_phone);
        ft.addToBackStack(null);
        ft.commit();


    }

}




