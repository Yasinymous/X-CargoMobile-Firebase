package com.example.xcargomobile.admin;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import com.example.xcargomobile.R;
import com.example.xcargomobile.cargo.*;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.StorageReference;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Addcargoinfo extends Fragment {

    public static final String TAG = "add cargo";
    EditText addlength, addwidth, addheight, addprice;

    Button addcargo;

    private Boolean IsCheck = false;
    private FirebaseUser user;
    private DatabaseReference dReference;
    private StorageReference storageReference;
    private FirebaseAuth fAuth;
    private FirebaseFirestore fStore;
    private String userID;

    private Cargo selectCargo;
    private CargoTracking cargoTracking;


    private static final String ARG_PARAM1 = "sname";
    private static final String ARG_PARAM2 = "slastname";
    private static final String ARG_PARAM3 = "sadress";
    private static final String ARG_PARAM4 = "sprovince";
    private static final String ARG_PARAM5 = "sdistric";
    private static final String ARG_PARAM6 = "rname";
    private static final String ARG_PARAM7 = "rlastname";
    private static final String ARG_PARAM8 = "radress";
    private static final String ARG_PARAM9 = "rprovince";
    private static final String ARG_PARAM10 = "rdistric";

    private String mParam1,mParam2,mParam3,mParam4,mParam5
            ,mParam6,mParam7,mParam8,mParam9,mParam10;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
            mParam3 = getArguments().getString(ARG_PARAM3);
            mParam4 = getArguments().getString(ARG_PARAM4);
            mParam5 = getArguments().getString(ARG_PARAM5);
            mParam6 = getArguments().getString(ARG_PARAM6);
            mParam7 = getArguments().getString(ARG_PARAM7);
            mParam8 = getArguments().getString(ARG_PARAM8);
            mParam9 = getArguments().getString(ARG_PARAM9);
            mParam10 = getArguments().getString(ARG_PARAM10);
        }
    }



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.cargoinfo,container,false);



        addlength = view.findViewById(R.id.addlength);
        addheight = view.findViewById(R.id.addheight);
        addwidth = view.findViewById(R.id.addwidth);
        addprice = view.findViewById(R.id.addprice);

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        Button addcargo =(Button)view.findViewById(R.id.addcargo);
        final NavController navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);
        addcargo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                double l = Integer.parseInt(addlength.getText().toString());
                double h = Integer.parseInt(addheight.getText().toString());
                double w = Integer.parseInt(addwidth.getText().toString());
                //double p = Integer.parseInt( addprice.getText().toString());

                if(TextUtils.isEmpty(addlength.getText())){
                    addlength.setError("Length is Required.");
                    return;
                }
                if(TextUtils.isEmpty(addheight.getText())){
                    addheight.setError("Heigth is Required.");
                    return;
                }
                if(TextUtils.isEmpty(addwidth.getText())){
                    addwidth.setError("Width is Required.");
                    return;
                }


                Cargo cargo_class = new Cargo();
                double p = (l*h*w) * 1.2;
                cargo_class.setUID(unique_id(view));
                cargo_class.setSender(new Sender(mParam1,mParam2,mParam3,mParam4,mParam5));
                cargo_class.setReceiver(new Receiver(mParam6,mParam7,mParam8,mParam9,mParam10));
                cargo_class.setInformation(new Information(l,h,w,p));
                Map<String, Object> cargo = new HashMap<>();
                cargo.put("sendername", cargo_class.getSender().getName());
                cargo.put("senderlastname", cargo_class.getSender().getLastName());
                cargo.put("senderadress", cargo_class.getSender().getAddress());
                cargo.put("senderprovince", cargo_class.getSender().getProvince());
                cargo.put("senderdistric", cargo_class.getSender().getDistrict());
                cargo.put("receivername", cargo_class.getReceiver().getName());
                cargo.put("receiverlastname", cargo_class.getReceiver().getLastName());
                cargo.put("receiveradress", cargo_class.getReceiver().getAddress());
                cargo.put("receiverprovince", cargo_class.getReceiver().getProvince());
                cargo.put("receiverdistric", cargo_class.getReceiver().getDistrict());
                cargo.put("length", cargo_class.getInformation().getLength());
                cargo.put("heigth", cargo_class.getInformation().getHeight());
                cargo.put("width", cargo_class.getInformation().getWidth());
                cargo.put("price", cargo_class.getInformation().getPrice());
                DocumentReference documentReference = fStore.collection("cargoes").document(cargo_class.getUID());
                documentReference.set(cargo).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Bundle bundle = new Bundle();
                        bundle.putString("status","success");
                        navController.navigate(R.id.action_addcargoinfo_to_home2,bundle);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d(TAG, "onFailure: " + e.toString());
                    }
                });


            }
        });
        return view;
    }

    private String unique_id(View view){
        final String uuid = UUID.randomUUID().toString();
        fStore.collection("cargoes")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                if(uuid.equals(document.getId()))
                                    unique_id(view);
                            }
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });
        return uuid;
    }


}
