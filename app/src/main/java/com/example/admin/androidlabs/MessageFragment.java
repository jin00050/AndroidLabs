package com.example.admin.androidlabs;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MessageFragment extends Fragment {

    View view;
    Button deleteButton;
    TextView message_TextView;
    TextView id_TextView;
    Boolean tablet_mode;

    public MessageFragment() {
        tablet_mode = false;
    }

    @SuppressLint("ValidFragment")
    public MessageFragment(ChatWindowActivity chatWindowActivity) {
        tablet_mode = true;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.activity_message_fragment, container, false);

        final Bundle bundle_back = this.getArguments();
        String single_message = bundle_back.getString("message");
        message_TextView = view.findViewById(R.id.message_TextView);
        message_TextView.setText(single_message);

        final Long single_ID = bundle_back.getLong("DB_ID");
        id_TextView = view.findViewById(R.id.id_TextView);
        id_TextView.setText(String.valueOf(single_ID));

        final int single_position = bundle_back.getInt("position");
        deleteButton = view.findViewById(R.id.deleteButton);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (getActivity().getLocalClassName().equals("MessageDetails")) {
                    Intent intent = new Intent();
                    intent.putExtra("bundle_back", bundle_back);
                    getActivity().setResult(2, intent);
                    getActivity().finish();
                } else {

                    ((ChatWindowActivity) getActivity()).delete(single_ID, single_position);
                }
            }
        });
        return view;
    }
}