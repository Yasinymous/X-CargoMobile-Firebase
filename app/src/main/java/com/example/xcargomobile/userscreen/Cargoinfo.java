package com.example.xcargomobile.userscreen;

import android.view.View;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.example.xcargomobile.R;

public class Cargoinfo extends AppCompatActivity {

    EditText sendername, senderlastname, senderadress, senderprovince, senderdistric
            , receivername, receiverlastname, receiverradress, receiverprovince, receiverdistric
            , length, width, height, price;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.usercargoinfo);

    }

}
