package com.example.admin.androidlabs;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import static com.example.admin.androidlabs.ChatDatabaseHelper.key_message;

public class ChatWindowActivity extends Activity {

    ListView listView;
    EditText editText;
    Button sendButten;
    ArrayList<String> information;
    ChatAdapter messageAdapter;
    protected static final String ACTIVITY_NAME = "ChatWindowActivity ";
    ContentValues cValues;
    ChatDatabaseHelper chatHelper;
    SQLiteDatabase db;
    Cursor c;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_window);
        information = new ArrayList<>();
        listView = (ListView) findViewById(R.id.ListView);
        editText = (EditText) findViewById(R.id.EditText1);
        sendButten = (Button) findViewById(R.id.sendButton);

        messageAdapter =new ChatAdapter( this );
        listView.setAdapter (messageAdapter);

         chatHelper= new ChatDatabaseHelper(this);
         db = chatHelper.getWritableDatabase();
         c = db.rawQuery("select * from " + chatHelper.DB_table, null);

         c.moveToFirst();

            while (!c.isAfterLast()) {
                int colIndex = c.getColumnIndex( key_message);
                information.add(c.getString(colIndex));
                Log.i(ACTIVITY_NAME, "SQL MESSAGE:" + c.getString(colIndex));
                c.moveToNext();
            }

         Log.i(ACTIVITY_NAME, "Cursor’s  column count =" + c.getColumnCount() );

         for(int i = 0; i < c.getColumnCount(); i++) {
             Log.i(ACTIVITY_NAME,c.getColumnName(i));
         }

         sendButten.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 information.add(editText.getText().toString());
                messageAdapter.notifyDataSetChanged(); //this restarts the process of getCount()/getView();
                editText.setText("");

                 cValues =new ContentValues();
                 cValues.put(key_message,editText.getText().toString());
                 db.insert(chatHelper.DB_table, null,cValues);

//                 messageAdapter.notifyDataSetChanged(); //this restarts the process of getCount()/getView();
//                 editText.setText("");
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
    @Override
    protected void onResume() {
        super.onResume();
        Log.i(ACTIVITY_NAME, "In onResume()");
    }
    @Override
    protected void onStart() {
        super.onStart();
        Log.i(ACTIVITY_NAME, "In onStart()");
    }
    @Override
    protected void onPause() {
        super.onPause();
        Log.i(ACTIVITY_NAME, "In onPause()");
    }
    @Override
    protected void onStop() {
        super.onStop();
        Log.i(ACTIVITY_NAME, "In onStop()");
    }
    public void onDestroy(){
       super.onDestroy();
        db.close();
        c.close();
        Log.i(ACTIVITY_NAME, "In onDestroy()");
    }
}




