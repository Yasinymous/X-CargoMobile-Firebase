package com.example.xcargomobile.userscreen;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.example.xcargomobile.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.*;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.HashMap;

public class Searchcargo extends Fragment {
    public static final String TAG = "search cargo";
    View myView;
    Button search_button;
    EditText searchcargo_id;
    ListView cargolist;

    private StorageReference storageReference;
    private FirebaseAuth fAuth;
    private FirebaseFirestore fStore;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.searchcargo, container,false);


        
        searchcargo_id = root.findViewById(R.id.searchcargo_id);
        cargolist = root.findViewById(R.id.cargolist);

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference();


        final ArrayList<HashMap<String,String>> list = new ArrayList<HashMap<String,String>>();
        //final ArrayAdapter adapter = new ArrayAdapter(getActivity(),android.R.layout.simple_list_item_1,list);
        SimpleAdapter adapter = new SimpleAdapter(
                getActivity(),
                list,
                R.layout.testlist,
                new String[] {"sfullname","sadress","rfullname","radress"},
                new int[] {R.id.sfullname, R.id.sadress, R.id.rfullname, R.id.radress}
        );




        Button search_button = (Button) root.findViewById(R.id.search_button);
        search_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                list.clear();
                cargolist.setAdapter(adapter);
                String searchbox = searchcargo_id.getText().toString();
                if (searchbox.isEmpty()){
                    searchcargo_id.setError("Search text is Required.");
                    return;
                }


                DocumentReference docRef = fStore.collection("cargoes").document(searchbox);
                docRef.addSnapshotListener(MetadataChanges.INCLUDE, new EventListener<DocumentSnapshot>() {
                    @Override
                    public void onEvent(@Nullable DocumentSnapshot snapshot,
                                        @Nullable FirebaseFirestoreException e) {
                        if (e != null) {
                            Log.w(TAG, "Listen failed.", e);
                            searchcargo_id.setError("Cargo Not Found.");
                            return;
                        }
                        if (snapshot != null && snapshot.exists()) {
                            //Log.d(TAG, "Current data: " + snapshot.getData());
                            String value = snapshot.getId();
                            String sname =  snapshot.getData().get("sendername").toString();
                            String slastname =  snapshot.getData().get("senderlastname").toString();
                            String sadress =  snapshot.getData().get("senderadress").toString();

                            String rname =  snapshot.getData().get("receivername").toString();
                            String rlastname =  snapshot.getData().get("receiverlastname").toString();
                            String radress =  snapshot.getData().get("receiveradress").toString();

                            HashMap<String,String> temp = new HashMap<String,String>();
                            temp.put("cargoid", value);
                            temp.put("sfullname",sname+" "+slastname);
                            temp.put("sadress", sadress);
                            temp.put("rfullname", rname+" "+rlastname);
                            temp.put("radress", radress);
                            list.add(temp);
                            cargolist.setAdapter(adapter);


                        } else {
                            Log.d(TAG, "Current data: null");
                            searchcargo_id.setError("Cargo Not Found.");
                            list.clear();
                            cargolist.setAdapter(adapter);
                            return;
                        }
                    }
                });
            }
        });

        cargolist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String cargoid = list.get(position).get("cargoid");
                //String text = parent.getItemAtPosition(position).toString();
                Intent i = new Intent(getActivity(), Cargoinfo.class);
                i.putExtra("cargoid", cargoid);
                startActivity(i);
                //editText.setText(cargoid, TextView.BufferType.EDITABLE);
            }
        });



        return root;


    }

}

