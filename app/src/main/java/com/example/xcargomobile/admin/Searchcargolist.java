package com.example.xcargomobile.admin;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import com.example.xcargomobile.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.*;
import com.google.firebase.firestore.*;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class Searchcargolist extends Fragment {
    public static final String TAG = "search cargo";
    View myView;
    Button search_buttonlist;
    EditText searchcargolist_id;
    ListView cargolist;

    private Boolean IsCheck = false;
    private FirebaseUser user;
    private DatabaseReference dReference;
    private StorageReference storageReference;
    private FirebaseAuth fAuth;
    private FirebaseFirestore fStore;
    private String userID;
    private String[] ulkeler =
            {"YSN-5561-1", "YSN-5561-2", "YSN-5561-3", "YSN-5561-4", "YSN-5561-5"
            ,"YSN-5561-6", "YSN-5561-7", "YSN-5561-8", "YSN-5561-9", "YSN-5561-10"};

    private static ArrayList<Type> mArrayList = new ArrayList<>();;


    private FirebaseDatabase mydatabase;
    private DatabaseReference myref;
    private RecyclerView test;

    public Searchcargolist() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.searchcargolist, container, false);


        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference();
        final String userID = fAuth.getCurrentUser().getUid();


        EditText editText = (EditText) root.findViewById(R.id.searchcargolist_id);
        Button search_buttonlist = (Button) root.findViewById(R.id.search_buttonlist);
        ListView cargolist = (ListView) root.findViewById(R.id.cargolist);
/*
        ArrayAdapter<String> veriAdaptoru = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_list_item_1, ulkeler);
        cargolist.setAdapter(veriAdaptoru);
*/

        final ArrayList<String> list = new ArrayList<>();
        final ArrayAdapter adapter = new ArrayAdapter(getActivity(),android.R.layout.simple_list_item_1,list);



        fStore.collection("cargoes")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot snapshots,
                                        @Nullable FirebaseFirestoreException e) {
                        if (e != null) {
                            Log.w(TAG, "listen:error", e);
                            return;
                        }

                        for (DocumentChange dc : snapshots.getDocumentChanges()) {
                            String value = dc.getDocument().getId();
                           // System.out.println("Deger: " + value);
                            list.add(value);
                            cargolist.setAdapter(adapter);
                            adapter.notifyDataSetChanged();
                        }

                    }
                });

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





