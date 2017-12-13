package com.example.admin.androidlabs;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import static com.example.admin.androidlabs.ChatDatabaseHelper.DB_Name;
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
    boolean isTablet;
    View result;
    long ID;
    FrameLayout tabletFrame;
    String message;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_window);
        information = new ArrayList<>();
        listView = (ListView) findViewById(R.id.ListView);
        editText = (EditText) findViewById(R.id.EditText1);
        sendButten=findViewById(R.id.sendButton);

        messageAdapter =new ChatAdapter( this );
        listView.setAdapter (messageAdapter);

        chatHelper= new ChatDatabaseHelper(this);
         db = chatHelper.getWritableDatabase();
         c = db.rawQuery("select * from " + chatHelper.DB_table, null);
         c.moveToFirst();

        while (!c.isAfterLast()) {
           int colIndex = c.getColumnIndex(ChatDatabaseHelper.key_message);
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
                String input = editText.getText().toString();
                information.add(input);
                messageAdapter.notifyDataSetChanged(); //this restarts the process of getCount()/getView();
                editText.setText("");

                cValues =new ContentValues();
                cValues.put(key_message,input);
                db.insert(chatHelper.DB_table,"",cValues);
            }
        });

       tabletFrame = findViewById(R.id.Lab7_tablet_frameLayout);
        if(tabletFrame==null){
            isTablet= false;
        Log.i(ACTIVITY_NAME,"the frameLayout does not exist, it is on the phone");}
        else {
            isTablet= true ;
        Log.i(ACTIVITY_NAME,"the frameLayout exists, it is on the tablet");
        }


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1,
                                    int position, long id) {
               message = messageAdapter.getItem(position);
               Long DB_ID= messageAdapter.getItemId(position);

                Bundle bundle = new Bundle();
                bundle.putString("message",message);
                bundle.putLong("DB_ID",DB_ID);
                bundle.putInt("position",position);

                if(isTablet==false) {
                    Intent i = new Intent(ChatWindowActivity.this, MessageDetails.class);
                    i.putExtra("bundle_phone",bundle);
                    startActivityForResult(i,2);
                }
                else{
                    MessageFragment  tablet_fragment= new MessageFragment();
                    tablet_fragment.setArguments(bundle);
                    FragmentTransaction ft = getFragmentManager().beginTransaction();
                    ft.replace(R.id.Lab7_tablet_frameLayout , tablet_fragment);
                    ft.commit();}
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
            result = null ;

            if(position%2 == 0)
                result = inflater.inflate(R.layout.chat_row_incoming, null);
            else
                result = inflater.inflate(R.layout.chat_row_outgoing, null);

            TextView message = (TextView)result.findViewById(R.id.message_text);
            message.setText(getItem(position)); // get the string at position
            return result;
        }
        public long getItemId(int position){
            c.moveToPosition(position);
            ID =c.getLong(c.getColumnIndex(ChatDatabaseHelper.key_RowID));
            return ID;
        }
    }
    protected void onActivityResult(int requestCode,int resultCode, Intent data) {
          if(resultCode == 2){
              Bundle bundle_return = data.getBundleExtra("bundle_back");
              Long delete_ID= bundle_return.getLong("DB_ID");
              int delete_position= bundle_return.getInt("position");
              delete(delete_ID,delete_position);
          }
    }
    public void delete(Long id,int position){
//        db.delete(ChatDatabaseHelper.DB_table , ChatDatabaseHelper.key_RowID  + " = " + id, null);
        chatHelper.deleteItem(id.toString());
        information.remove(position);
        messageAdapter.notifyDataSetChanged();
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


//    public void refreshActivity(){
//        finish();
//        Intent intent = getIntent();
//        startActivity(intent);
//    }
}




