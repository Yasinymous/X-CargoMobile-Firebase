package com.example.xcargomobile.admin;
import android.text.InputType;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.example.xcargomobile.R;

public class Cargoedit extends AppCompatActivity {

    EditText sendername, senderlastname, senderadress, senderprovince, senderdistric
            , receivername, receiverlastname, receiveradress, receiverprovince, receiverdistric
            , length, width, height, price;

    Button cargoeditbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cargoedit);

        cargoeditbtn = (Button)findViewById(R.id.cargoeditbtn);


        cargoeditbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(cargoeditbtn.getText().equals("Apply")){
                    editonoff(false);
                    cargoeditbtn.setText("Edit");
                }
                else{
                    editonoff(true);
                    cargoeditbtn.setText("Apply");
                }
            }
        });

        sendername = (EditText)findViewById(R.id.sendername);
        senderlastname = (EditText)findViewById(R.id.senderlastname);
        senderadress = (EditText)findViewById(R.id.senderadress);
        senderprovince = (EditText)findViewById(R.id.senderprovince);
        senderdistric = (EditText)findViewById(R.id.senderdistric);

        receivername = (EditText)findViewById(R.id.receivername);
        receiverlastname = (EditText)findViewById(R.id.receiverlastname);
        receiveradress = (EditText)findViewById(R.id.receiveradress);
        receiverprovince = (EditText)findViewById(R.id.receiverprovince);
        receiverdistric = (EditText)findViewById(R.id.receiverdistric);

        length = (EditText)findViewById(R.id.length);
        width = (EditText)findViewById(R.id.width);
        height =  (EditText)findViewById(R.id.height);
        price = (EditText)findViewById(R.id.price);
        editonoff(false);


    }
    public void editonoff(boolean x){
        lock(sendername,x);
        lock(senderlastname,x);
        lock(senderadress,x);
        lock(senderprovince,x);
        lock(senderdistric,x);

        lock(receivername,x);
        lock(receiverlastname,x);
        lock(receiveradress,x);
        lock(receiverprovince,x);
        lock(receiverdistric,x);

        lock(length,x);
        lock(width,x);
        lock(height,x);
        lock(price,x);
    }

    public void lock(EditText editText,boolean x){
        editText.setEnabled(x);
    }


}
