package com.example.admin.androidlabs;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class ChatWindowActivity extends Activity {

    ListView listView;
    EditText editText;
    Button sendButten;
    ArrayList<String> information = new ArrayList<>();;
    ChatAdapter messageAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_window);

        listView =  findViewById(R.id.ListView);
        editText =  findViewById(R.id.EditText1);
        sendButten = (Button) findViewById(R.id.sendButton);

        messageAdapter =new ChatAdapter( this );
        listView.setAdapter (messageAdapter);


        sendButten.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                information.add(editText.getText().toString());
                messageAdapter.notifyDataSetChanged(); //this restarts the process of getCount()/getView();
                editText.setText("");

            }
        });

    }

    private class ChatAdapter extends ArrayAdapter<String> {

        public ChatAdapter(Context ctx) {
            super(ctx, 0);
        }

        public int getCount() {
            return information.size();
        }

        public String getItem(int position) {
            return information.get(position);
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = ChatWindowActivity.this.getLayoutInflater();
            View result = null ;

            if(position%2 == 0)
                result = inflater.inflate(R.layout.chat_row_incoming, null);
            else
                result = inflater.inflate(R.layout.chat_row_outgoing, null);

            TextView message = (TextView)result.findViewById(R.id.message_text);
            message.setText(   getItem(position)  ); // get the string at position
            return result;


        }


    }

}




