package com.example.xcargomobile.admin;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import com.example.xcargomobile.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.*;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;

public class Searchcargolist extends Fragment {
    public static final String TAG = "search cargo";
    View myView;
    Button search_buttonlist;
    EditText searchcargolist_id;
    ListView cargolist;
    ImageView cargostatus;
    Switch switch1;

    private Boolean IsCheck = false;
    private FirebaseUser user;
    private DatabaseReference dReference;
    private StorageReference storageReference;
    private FirebaseAuth fAuth;
    private FirebaseFirestore fStore;
    private String userID;


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

        EditText searchText = (EditText) root.findViewById(R.id.searchcargolist_id);
        Button search_buttonlist = (Button) root.findViewById(R.id.search_buttonlist);
        ListView cargolist = (ListView) root.findViewById(R.id.cargolist);
        switch1 = (Switch) root.findViewById(R.id.switch1);
/*
        ArrayAdapter<String> veriAdaptoru = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_list_item_1, ulkeler);
        cargolist.setAdapter(veriAdaptoru);
*/

        final ArrayList<HashMap<String,String>> list = new ArrayList<HashMap<String,String>>();
                //final ArrayAdapter adapter = new ArrayAdapter(getActivity(),android.R.layout.simple_list_item_1,list);
        SimpleAdapter adapter = new SimpleAdapter(
                getActivity(),
                list,
                R.layout.testlist,
                new String[] {"sfullname","sadress","rfullname","radress"},
                new int[] {R.id.sfullname, R.id.sadress, R.id.rfullname, R.id.radress}
        );



        search_buttonlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                list.clear();
                cargolist.setAdapter(adapter);
                String searchbox = searchText.getText().toString();
                if (searchbox.isEmpty()){
                    searchText.setError("Search text is Required.");
                    return;
                }
                if (switch1.isChecked()){
                    DocumentReference docRef = fStore.collection("cargoes").document(searchbox);
                    docRef.addSnapshotListener(MetadataChanges.INCLUDE, new EventListener<DocumentSnapshot>() {
                        @Override
                        public void onEvent(@Nullable DocumentSnapshot snapshot,
                                            @Nullable FirebaseFirestoreException e) {
                            if (e != null) {
                                Log.w(TAG, "Listen failed.", e);
                                searchText.setError("Cargo Not Found.");
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
                                searchText.setError("Cargo Not Found.");
                                list.clear();
                                cargolist.setAdapter(adapter);
                                return;
                            }
                        }
                    });

                }

                else{

                    String[] searchmetod = {"sendername","senderlastname","senderadress"
                            ,"receivername","receiverlastname","receiveradress"};
                    for(int i = 0; i<6;i++){

                        fStore.collection("cargoes")
                                .whereEqualTo(searchmetod[i], searchbox)
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {
                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                // Log.d(TAG, document.getId() + " => " + document.getData());
                                                String value = document.getId();
                                                String sname = document.get("sendername").toString();
                                                String rname = document.get("receivername").toString();
                                                String slastname = document.get("senderlastname").toString();
                                                String rlastname = document.get("receiverlastname").toString();
                                                String sadress = document.get("senderadress").toString();
                                                String radress = document.get("receiveradress").toString();
                                                HashMap<String,String> temp = new HashMap<String,String>();
                                                temp.put("cargoid", value);
                                                temp.put("sfullname",sname+" "+slastname);
                                                temp.put("sadress", sadress);
                                                temp.put("rfullname", rname+" "+rlastname);
                                                temp.put("radress", radress);
                                                list.add(temp);
                                                cargolist.setAdapter(adapter);
                                            }
                                        } else {
                                            Log.d(TAG, "Error getting documents: ", task.getException());
                                            list.clear();
                                            cargolist.setAdapter(adapter);
                                            searchText.setError("Cargo Not Found.");
                                            return;
                                        }
                                    }
                                });
                            }
                }
            }
        });

        cargolist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String cargoid = list.get(position).get("cargoid");
                //String text = parent.getItemAtPosition(position).toString();
                Intent i = new Intent(getActivity(), Cargoedit.class);
                i.putExtra("cargoid", cargoid);
                startActivity(i);
                //editText.setText(cargoid, TextView.BufferType.EDITABLE);
            }
        });


        return root;

    }



}


 /*
        // cargolist.invalidateViews();
        fStore.collection("cargoes").whereEqualTo("sendername",searchbox)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot snapshots,
                                        @Nullable FirebaseFirestoreException e) {
                        if (e != null) {
                            searchText.setError("Cargo Not Found.");
                            return;
                        }

                        for (DocumentChange dc : snapshots.getDocumentChanges()) {
                            String value = dc.getDocument().getId();
                            String sname = dc.getDocument().get("sendername").toString();
                            String rname = dc.getDocument().get("receivername").toString();
                            String slastname = dc.getDocument().get("senderlastname").toString();
                            String rlastname = dc.getDocument().get("receiverlastname").toString();
                            String sadress = dc.getDocument().get("senderadress").toString();
                            String radress = dc.getDocument().get("receiveradress").toString();
                            // System.out.println("Deger: " + value);
                            HashMap<String,String> temp = new HashMap<String,String>();
                            temp.put("cargoid", value);
                            temp.put("sfullname",sname+" "+slastname);
                            temp.put("sadress", sadress);
                            temp.put("rfullname", rname+" "+rlastname);
                            temp.put("radress", radress);
                            list.add(temp);
                            cargolist.setAdapter(adapter);
                            //adapter.notifyDataSetChanged();
                            //adapter.notifyDataSetInvalidated();


                        }
                    }
                });


*/



