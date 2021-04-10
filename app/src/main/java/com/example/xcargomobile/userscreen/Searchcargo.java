package com.example.xcargomobile.userscreen;


import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import androidx.fragment.app.Fragment;
import com.example.xcargomobile.MainActivity;
import com.example.xcargomobile.R;
import com.example.xcargomobile.userscreen.Cargoinfo;

public class Searchcargo extends Fragment {


    View myView;
    Button search_button;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.searchcargo, container,false);


        Button search_button = (Button) root.findViewById(R.id.search_button);
        search_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    Intent intent = new Intent(getActivity(),Cargoinfo.class);
                    startActivity(intent);
            }
        });
        return root;


    }

}
