package com.example.xcargomobile.admin;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.example.xcargomobile.R;
import com.example.xcargomobile.userscreen.Settings;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.annotations.NotNull;
import com.google.firebase.firestore.*;
import com.google.firebase.storage.StorageReference;
import android.content.Intent;

import java.util.HashMap;
import java.util.Map;

public class Cargoedit extends AppCompatActivity {
    public static final String TAG = "add cargo";
    EditText sendername, senderlastname, senderadress, senderprovince, senderdistric
            , receivername, receiverlastname, receiveradress, receiverprovince, receiverdistric
            , length, width, height, price;

    Button cargoeditbtn;

    private FirebaseUser user;
    private DatabaseReference dReference;
    private StorageReference storageReference;
    private FirebaseAuth fAuth;
    private FirebaseFirestore fStore;
    private String userID;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cargoedit);

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

        cargoeditbtn = (Button)findViewById(R.id.cargoeditbtn);


        cargoeditbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(cargoeditbtn.getText().equals("Apply")){
                    editonoff(false);
                    cargoeditbtn.setText("Edit");

                    String sname = sendername.getText().toString();
                    String slastname = senderlastname.getText().toString();
                    String sadress = senderadress.getText().toString();
                    String sprovince = senderprovince.getText().toString();
                    String sdistric = senderdistric.getText().toString();
                    String rname = receivername.getText().toString();
                    String rlastname = receiverlastname.getText().toString();
                    String radress = receiveradress.getText().toString();
                    String rprovince = receiverprovince.getText().toString();
                    String rdistric = receiverdistric.getText().toString();

                    double l = Integer.parseInt(length.getText().toString());
                    double h = Integer.parseInt(height.getText().toString());
                    double w = Integer.parseInt(width.getText().toString());


                    if(TextUtils.isEmpty(sname)){
                        sendername.setError("Name is Required.");
                        return;
                    }
                    if(TextUtils.isEmpty(slastname)){
                        senderlastname.setError("Last Name is Required.");
                        return;
                    }
                    if(TextUtils.isEmpty(sadress)){
                        senderadress.setError("Adress is Required.");
                        return;
                    }
                    if(TextUtils.isEmpty(sprovince)){
                        senderprovince.setError("Province is Required.");
                        return;
                    }
                    if(TextUtils.isEmpty(sdistric)){
                        senderdistric.setError("Distric is Required.");
                        return;
                    }
                    if(TextUtils.isEmpty(rname)){
                        receivername.setError("Name is Required.");
                        return;
                    }
                    if(TextUtils.isEmpty(rlastname)){
                        receiverlastname.setError("Last Name is Required.");
                        return;
                    }
                    if(TextUtils.isEmpty(radress)){
                        receiveradress.setError("Adress is Required.");
                        return;
                    }
                    if(TextUtils.isEmpty(rprovince)){
                        receiverprovince.setError("Province is Required.");
                        return;
                    }
                    if(TextUtils.isEmpty(rdistric)){
                        receiverdistric.setError("Distric is Required.");
                        return;
                    }
                    /*
                    if(TextUtils.isEmpty(l)){
                        length.setError("Length is Required.");
                        return;
                    }
                    if(TextUtils.isEmpty(h)){
                        height.setError("Heigth is Required.");
                        return;
                    }
                    if(TextUtils.isEmpty(w)){
                        width.setError("Width is Required.");
                        return;
                    }

                     */
                    double p = (l*h*w) * 1.2;

                    Map cargoUpdateMap = new HashMap();
                    cargoUpdateMap.put("sendername", sname);
                    cargoUpdateMap.put("senderlastname", slastname);
                    cargoUpdateMap.put("senderadress", sadress);
                    cargoUpdateMap.put("senderprovince", sprovince);
                    cargoUpdateMap.put("senderdistric", sdistric);

                    cargoUpdateMap.put("receivername", rname);
                    cargoUpdateMap.put("receiverlastname", rlastname);
                    cargoUpdateMap.put("receiveradress", radress);
                    cargoUpdateMap.put("receiverprovince", rprovince);
                    cargoUpdateMap.put("receiverdistric", rdistric);

                    cargoUpdateMap.put("length", l);
                    cargoUpdateMap.put("height", h);
                    cargoUpdateMap.put("width", w);
                    cargoUpdateMap.put("price", p);

                    fStore.collection("cargoes").document(cargoid).update(cargoUpdateMap).addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull @NotNull Task<DocumentSnapshot> task) {
                            if (task.isSuccessful()){
                                Toast.makeText(Cargoedit.this, "Update Cargo", Toast.LENGTH_SHORT).show();
                            }
                            else {
                                Toast.makeText(Cargoedit.this, "Not Cargo Update...", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
                else{
                    editonoff(true);
                    cargoeditbtn.setText("Apply");
            }
            }
        });

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
/*
        fStore.collection("cargoes").document(cargoid).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>(){
            @Override
            public void onComplete(@NonNull @org.jetbrains.annotations.NotNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()){
                    String sname = task.getResult().getString("sendername");
                    String slastname = task.getResult().getString("senderlastname");
                    String sadress = task.getResult().getString("senderadress");
                    String sprovince = task.getResult().getString("senderprovince");
                    String sdistric = task.getResult().getString("senderdistric");

                    String rname = task.getResult().getString("receivername");
                    String rlastname = task.getResult().getString("receiverlastname");
                    String radress = task.getResult().getString("receiverdistricadress");
                    String rprovince = task.getResult().getString("receiverprovince");
                    String rdistric = task.getResult().getString("receiverdistric");

                    double length1 = Double.parseDouble(task.getResult().getString("length"));
                    double heigth1 = Double.parseDouble(task.getResult().getString("height"));
                    double width1 = Double.parseDouble(task.getResult().getString("width"));
                    double price1 = Double.parseDouble(task.getResult().getString("price"));

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

                    length.setText(length1+"");
                    height.setText(heigth1+"");
                    width.setText(width1+"");
                    price.setText(price1+"");

                }

            }
        });

*/

/*
        fStore.collection("cargoes")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());
                            }
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });

      */              editonoff(false);


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

    public String cargoid(String cid){
        return cid;
    }


}
