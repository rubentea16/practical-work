package com.example.ruben.rfid_hampir;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.client.Firebase;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * Created by Ruben on 1/12/2017.
 */

public class absensi extends Fragment{
    public absensi(){}

    private FirebaseDatabase mDatabase;

    RecyclerView rvView;
    ArrayList<String> list = new ArrayList<>();
    AdapterAbsensi adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getActivity().setTitle("Absensi");
        return inflater.inflate(R.layout.absensi , container ,false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Firebase.setAndroidContext(getActivity());
        rvView = (RecyclerView) getActivity().findViewById(R.id.rvAbsensi);
        adapter = new AdapterAbsensi(list);

        mDatabase = FirebaseDatabase.getInstance();

        DatabaseReference myRef = mDatabase.getReference("feeds");

        myRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                String value = dataSnapshot.getValue(String.class);
                list.add(0, value);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        LinearLayoutManager manager = new LinearLayoutManager(getActivity().getBaseContext());
        rvView.setHasFixedSize(true);
        rvView.setLayoutManager(manager);
        rvView.setAdapter(adapter);

        DatabaseReference myRef1 = mDatabase.getReference("Jadwal/Praktikum");

        myRef1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                global.DataAbsensi = dataSnapshot.getValue(String.class);
                final String DAS[]= global.DataAbsensi.split("#");
                global.gHari = DAS[0];
                final String DAS2[] = DAS[1].split(":");
                global.gJam = DAS2[0];
                global.gMenit = DAS2[1];
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



    }
}
