package com.example.xcargomobile.admin;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;
import androidx.fragment.app.Fragment;
import com.example.xcargomobile.R;

public class Adminhome extends Fragment {

    View myView;
    Button toaddcargo;
    private static final String ARG_PARAM1 = "success";
    private String mParam1;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
        }
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.addcargohome, container,false);
        Button toaddcargo = (Button) root.findViewById(R.id.toaddcargo);

        if(mParam1!=null && mParam1.equals("success")){
                Toast.makeText(getActivity(), "Cargo Add Succesfull", Toast.LENGTH_SHORT).show();
        }


        toaddcargo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Addcargo.class);
                startActivity(intent);
            }
        });

        return root;
    }
}


