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

public class Addcargoreceiver extends Fragment {

    EditText addreceivername, addreceiverlastname, addreceiver
            , addreceiveradress, addreceiverprovince, addreceiverdistric;

    EditText addsendername;

    Button nextcargoinfo;

    private static final String ARG_PARAM1 = "sname";
    private static final String ARG_PARAM2 = "slastname";
    private static final String ARG_PARAM3 = "sadress";
    private static final String ARG_PARAM4 = "sprovince";
    private static final String ARG_PARAM5 = "sdistric";

    private String mParam1,mParam2,mParam3,mParam4,mParam5;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
            mParam3 = getArguments().getString(ARG_PARAM3);
            mParam4 = getArguments().getString(ARG_PARAM4);
            mParam5 = getArguments().getString(ARG_PARAM5);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View view =inflater.inflate(R.layout.receiverinfo,container,false);

        addreceivername = view.findViewById(R.id.addreceivername);
        addreceiverlastname = view.findViewById(R.id.addreceiverlastname);
        addreceiveradress = view.findViewById(R.id.addreceiveradress);
        addreceiverprovince = view.findViewById(R.id.addreceiverprovince);
        addreceiverdistric = view.findViewById(R.id.addreceiverdistric);


        Button nextcargoinfo =(Button) view.findViewById(R.id.nextcargoinfo);
        final NavController navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);

        nextcargoinfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String rname = addreceivername.getText().toString();
                String rlastname = addreceiverlastname.getText().toString();
                String radress = addreceiveradress.getText().toString();
                String rprovince = addreceiverprovince.getText().toString();
                String rdistric = addreceiverdistric.getText().toString();

                if(TextUtils.isEmpty(rname)){
                    addreceivername.setError("Name is Required.");
                    return;
                }
                if(TextUtils.isEmpty(rlastname)){
                    addreceiverlastname.setError("Last Name is Required.");
                    return;
                }
                if(TextUtils.isEmpty(radress)){
                    addreceiveradress.setError("Adress is Required.");
                    return;
                }
                if(TextUtils.isEmpty(rprovince)){
                    addreceiverprovince.setError("Province is Required.");
                    return;
                }
                if(TextUtils.isEmpty(rdistric)){
                    addreceiverdistric.setError("Distric is Required.");
                    return;
                }

                Bundle bundle = new Bundle();
                bundle.putString("sname", mParam1);
                bundle.putString("slastname", mParam2);
                bundle.putString("sadress", mParam3);
                bundle.putString("sprovince", mParam4);
                bundle.putString("sdistric", mParam5);
                bundle.putString("rname",rname);
                bundle.putString("rlastname", rlastname);
                bundle.putString("radress", radress);
                bundle.putString("rprovince", rprovince);
                bundle.putString("rdistric", rdistric);
                Navigation.findNavController(view).navigate(R.id.action_addcargoreceiver2_to_addcargoinfo,bundle);
                //navController.navigate(R.id.action_addcargoreceiver2_to_addcargoinfo);
            }

        });



        return view;
    }


}
