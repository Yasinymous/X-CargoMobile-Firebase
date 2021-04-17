package com.example.xcargomobile.admin;

import android.os.Bundle;
import android.text.TextUtils;
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
import com.example.xcargomobile.cargo.Cargo;
import com.example.xcargomobile.cargo.CargoTracking;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class Addcargosender extends Fragment {
    public static final String TAG = "add cargo";
    EditText addsendername, addsenderlastname, addsender
            , addsenderadress, addsenderprovince, addsenderdistric;

    Button nextsender;

    private Boolean IsCheck = false;
    private FirebaseUser user;
    private DatabaseReference dReference;
    private StorageReference storageReference;
    private FirebaseAuth fAuth;
    private FirebaseFirestore fStore;
    private String userID;

    private Cargo selectCargo;
    private CargoTracking cargoTracking;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        //fragment_home layout tanımlanıyor
        View view =inflater.inflate(R.layout.senderinfo,container,false);


        addsendername = view.findViewById(R.id.addsendername);
        addsenderlastname = view.findViewById(R.id.addsenderlastname);
        addsenderadress = view.findViewById(R.id.addsenderadress);
        addsenderprovince = view.findViewById(R.id.addsenderprovince);
        addsenderdistric = view.findViewById(R.id.addsenderdistric);


        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference();
        final String userID = fAuth.getCurrentUser().getUid();

        nextsender =(Button) view.findViewById(R.id.nextreceiver);
        final NavController navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);
        nextsender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sname = addsendername.getText().toString();
                String slastname = addsenderlastname.getText().toString();
                String sadress = addsenderadress.getText().toString();
                String sprovince = addsenderprovince.getText().toString();
                String sdistric = addsenderdistric.getText().toString();

                if(TextUtils.isEmpty(sname)){
                    addsendername.setError("Name is Required.");
                    return;
                }
                if(TextUtils.isEmpty(slastname)){
                    addsenderlastname.setError("Last Name is Required.");
                    return;
                }
                if(TextUtils.isEmpty(sadress)){
                    addsenderadress.setError("Adress is Required.");
                    return;
                }
                if(TextUtils.isEmpty(sprovince)){
                    addsenderprovince.setError("Province is Required.");
                    return;
                }
                if(TextUtils.isEmpty(sdistric)){
                    addsenderdistric.setError("Distric is Required.");
                    return;
                }


                //navigate metodu ile nav_graph.xml dosyasındaki hangi action kullanacağını belirterek, geçiş yapacağı fragment bilgisini veriyoruz

                Bundle bundle =new Bundle();
                bundle.putString("sname", sname);
                bundle.putString("slastname", slastname);
                bundle.putString("sadress", sadress);
                bundle.putString("sprovince", sprovince);
                bundle.putString("sdistric", sdistric);
                Navigation.findNavController(view).navigate(R.id.action_addcargosender2_to_addcargoreceiver2,bundle);
                //navController.navigate(R.id.action_addcargosender2_to_addcargoreceiver2);
            }
        });
        return view;
    }





}
