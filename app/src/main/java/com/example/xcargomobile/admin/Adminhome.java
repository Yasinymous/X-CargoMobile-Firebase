package com.example.xcargomobile.admin;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import androidx.fragment.app.Fragment;
import com.example.xcargomobile.MainActivity;
import com.example.xcargomobile.R;
import com.example.xcargomobile.userscreen.Cargoinfo;
import com.example.xcargomobile.admin.Addcargo;

public class Adminhome extends Fragment {

    View myView;
    Button toaddcargo;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.addcargohome, container,false);
        Button toaddcargo = (Button) root.findViewById(R.id.toaddcargo);
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


