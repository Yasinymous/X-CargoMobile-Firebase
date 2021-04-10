package com.example.xcargomobile.userscreen;

import android.content.Intent;
import android.graphics.Color;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import androidx.fragment.app.Fragment;
import com.example.xcargomobile.R;


public class Profile extends Fragment {

    public Profile() {
        // Required empty public constructor
    }
    EditText username,name,lastname;
    Button edit_save;
    TextView edit_info;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.profile, container,false);

        Button edit_save = (Button) root.findViewById(R.id.edit_save);
        lock(root,false);
        text_status(root,false);
        edit_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(edit_save.getText().equals("Apply")){
                    lock(root,false);
                    text_status(root,true);
                    edit_save.setText("Edit");
                }
                else{
                    lock(root,true);
                    text_status(root,false);
                    edit_save.setText("Apply");
                }
            }
        });

    return root;
    }

    public void lock(View root,boolean x){
            EditText uEdit = (EditText) root.findViewById(R.id.username);
            EditText nEdit = (EditText) root.findViewById(R.id.name);
            EditText lEdit = (EditText) root.findViewById(R.id.lastname);
            uEdit.setEnabled(x);
            nEdit.setEnabled(x);
            lEdit.setEnabled(x);
        }
    public void text_status(View root,boolean x){
        TextView textview = (TextView)root.findViewById(R.id.edit_info);
        if(x){
            textview.setTextColor(getResources().getColor(R.color.green_700));
            textview.setText("Succesfull");
        }
        else{
            textview.setTextColor(getResources().getColor(R.color.green_700));
            textview.setText("");
        }

    }
}



