//package com.example.admin.androidlabs;
//
//import android.app.Activity;
//import android.content.Context;
//import android.os.Bundle;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ArrayAdapter;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.ListAdapter;
//import android.widget.ListView;
//
//import java.util.ArrayList;
//
//public class ChatWindow_activity extends Activity {
//    EditText editText;
//    ArrayList<String>messages;
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_chat_window_activity);
//      //  String[] list ={};
//      //  ListAdapter lapt =new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,list);
//
//        messages = new ArrayList<>();
//        ListView lv =(ListView)findViewById(R.id.ListView);
//      //  lv.setAdapter(lapt);
//
//        editText = (EditText)findViewById(R.id.EditText);
//
//        Button btn = (Button)findViewById(R.id.button4);
//        btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//           messages.add(editText.getText());
//                }
//
//        } );
//
//        private ChatAdapter extends ArrayAdapter<String> {
//
//	     public ChatAdapter(Context ctx){
//                super(ctx, 0);
//            }
//         public int getCount() {
//                return messages.size();
//        }
//         public  String getItem( int position){
//                return messages.get(position);
//            }
//         public View getView( int position, View convertView, ViewGroup parent){
//        }
//}
//
//
//
//}
//}
