package com.example.xcargomobile.userscreen;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.example.xcargomobile.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.firestore.*;
import com.google.firebase.storage.StorageReference;

public class Cargoinfo extends AppCompatActivity {
    public static final String TAG = "Cargo info";
    EditText sendername, senderlastname, senderadress, senderprovince, senderdistric
            , receivername, receiverlastname, receiveradress, receiverprovince, receiverdistric
            , length, width, height, price;

    private FirebaseUser user;
    private DatabaseReference dReference;
    private StorageReference storageReference;
    private FirebaseAuth fAuth;
    private FirebaseFirestore fStore;
    private String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.usercargoinfo);



        Intent data = getIntent();

        String cargoid =  data.getStringExtra("cargoid");
        //System.out.println(cargoid);

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

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();


        DocumentReference docRef = fStore.collection("cargoes").document(cargoid);
        docRef.addSnapshotListener(MetadataChanges.INCLUDE, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot snapshot,
                                @Nullable FirebaseFirestoreException e) {
                if (e != null) {
                    Log.w(TAG, "Listen failed.", e);
                    return;
                }
                if (snapshot != null && snapshot.exists()) {
                    //Log.d(TAG, "Current data: " + snapshot.getData());
                    String sname =  snapshot.getData().get("sendername").toString();
                    String slastname =  snapshot.getData().get("senderlastname").toString();
                    String sadress =  snapshot.getData().get("senderadress").toString();
                    String sprovince =  snapshot.getData().get("senderprovince").toString();
                    String sdistric =  snapshot.getData().get("senderdistric").toString();

                    String rname =  snapshot.getData().get("receivername").toString();
                    String rlastname =  snapshot.getData().get("receiverlastname").toString();
                    String radress =  snapshot.getData().get("receiveradress").toString();
                    String rprovince =  snapshot.getData().get("receiverprovince").toString();
                    String rdistric =  snapshot.getData().get("receiverdistric").toString();

                    String length1 = snapshot.getData().get("length").toString();
                    String heigth1 = snapshot.getData().get("heigth").toString();
                    String width1 = snapshot.getData().get("width").toString();
                    String price1 = snapshot.getData().get("price").toString();

                    sendername.setText(sname);
                    senderlastname.setText(slastname);
                    senderadress.setText(sadress);
                    senderprovince.setText(sprovince);
                    senderdistric.setText(sdistric);

                    receivername.setText(rname);
                    receiverlastname.setText(rlastname);
                    receiveradress.setText(radress);
                    receiverprovince.setText(rprovince);
                    receiverdistric.setText(rdistric);

                    length.setText(length1);
                    height.setText(heigth1);
                    width.setText(width1);
                    price.setText(price1);

                } else {
                    Log.d(TAG, "Current data: null");
                }
            }
        });


    }

}
