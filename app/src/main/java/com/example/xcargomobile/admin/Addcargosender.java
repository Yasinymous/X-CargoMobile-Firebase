package com.example.xcargomobile.admin;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.viewpager.widget.ViewPager;
import com.example.xcargomobile.R;

public class Addcargosender extends Fragment {

    EditText addsendername, addsenderlastname, addsender
            , addsenderadress, addsenderprovince, addsenderdistric;

    Button nextreceiver;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        //fragment_home layout tanımlanıyor
        View view =inflater.inflate(R.layout.senderinfo,container,false);
        Button nextreceiver =(Button) view.findViewById(R.id.nextreceiver);
        final NavController navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);
        nextreceiver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //navigate metodu ile nav_graph.xml dosyasındaki hangi action kullanacağını belirterek, geçiş yapacağı fragment bilgisini veriyoruz
                navController.navigate(R.id.action_addcargosender2_to_addcargoreceiver2);
            }
        });
        return view;
    }



}
