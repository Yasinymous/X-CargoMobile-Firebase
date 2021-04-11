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

import static androidx.databinding.DataBindingUtil.setContentView;

public class Addcargoreceiver extends Fragment {

    EditText addreceivername, addreceiverlastname, addreceiver
            , addreceiveradress, addreceiverprovince, addreceiverdistric;

    Button nextcargoinfo;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View view =inflater.inflate(R.layout.receiverinfo,container,false);
        Button nextcargoinfo =(Button) view.findViewById(R.id.nextcargoinfo);
        final NavController navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);
        System.out.println("girdi");
        System.out.println(nextcargoinfo);
        nextcargoinfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("test2");
                //navigate metodu ile nav_graph.xml dosyasındaki hangi action kullanacağını belirterek, geçiş yapacağı fragment bilgisini veriyoruz
                navController.navigate(R.id.action_addcargoreceiver2_to_addcargoinfo);
            }
        });
        return view;
    }

}
