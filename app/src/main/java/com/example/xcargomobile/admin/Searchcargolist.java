package com.example.xcargomobile.admin;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.fragment.app.ListFragment;
import com.example.xcargomobile.R;
import com.example.xcargomobile.userscreen.Cargoinfo;

public class Searchcargolist extends Fragment {

    View myView;
    Button search_buttonlist;
    EditText searchcargolist_id;
    ListView cargolist;

    private String[] ulkeler =
            {"YSN-5561-1", "YSN-5561-2", "YSN-5561-3", "YSN-5561-4", "YSN-5561-5"
            ,"YSN-5561-6", "YSN-5561-7", "YSN-5561-8", "YSN-5561-9", "YSN-5561-10"};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.searchcargolist, container, false);

        EditText editText = (EditText) root.findViewById(R.id.searchcargolist_id);
        Button search_buttonlist = (Button) root.findViewById(R.id.search_buttonlist);
        ListView cargolist = (ListView) root.findViewById(R.id.cargolist);

        ArrayAdapter<String> veriAdaptoru = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_list_item_1, ulkeler);
        cargolist.setAdapter(veriAdaptoru);


        search_buttonlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Cargoedit.class);
                startActivity(intent);
            }
        });





        cargolist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String text = parent.getItemAtPosition(position).toString();
                editText.setText(text, TextView.BufferType.EDITABLE);
            }
        });


        return root;

    }


}



