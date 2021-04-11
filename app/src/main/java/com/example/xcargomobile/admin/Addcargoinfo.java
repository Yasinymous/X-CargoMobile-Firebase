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
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import com.example.xcargomobile.R;

public class Addcargoinfo extends Fragment {

    EditText addlength, addwidt, addheight, price;

    Button addcargo;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        //fragment_home layout tanımlanıyor
        View view =inflater.inflate(R.layout.cargoinfo,container,false);
        Button addcargo =(Button)view.findViewById(R.id.addcargo);
        final NavController navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);
        addcargo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("test3");
                //navigate metodu ile nav_graph.xml dosyasındaki hangi action kullanacağını belirterek, geçiş yapacağı fragment bilgisini veriyoruz
                navController.navigate(R.id.action_addcargoinfo_to_home2);
            }
        });
        return view;
    }

}
