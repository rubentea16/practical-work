package com.example.ruben.rfid_hampir;

import android.app.Fragment;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.app.NotificationCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.widget.Toast;


import com.firebase.client.Firebase;
import java.util.ArrayList;

import com.firebase.client.collection.LLRBNode;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import static android.R.attr.button;
import static android.content.Context.NOTIFICATION_SERVICE;
import static android.graphics.Color.CYAN;
import static com.example.ruben.rfid_hampir.global.namanotif;
import static com.example.ruben.rfid_hampir.global.notif;

public class history extends Fragment {
    public history(){}

    private FirebaseDatabase mDatabase;

    RecyclerView rvView;
    ArrayList<String> list = new ArrayList<>();
    AdapterHistory adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getActivity().setTitle("History");
        return inflater.inflate(R.layout.history , container ,false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Firebase.setAndroidContext(getActivity());
        rvView = (RecyclerView) getActivity().findViewById(R.id.rvView);
        adapter = new AdapterHistory(list);

        mDatabase = FirebaseDatabase.getInstance();

        DatabaseReference myRef = mDatabase.getReference("feeds");

        Query latestFeeds = myRef.limitToLast(100);

        latestFeeds.addChildEventListener(new com.google.firebase.database.ChildEventListener() {
            @Override
            public void onChildAdded(com.google.firebase.database.DataSnapshot dataSnapshot, String s) {
                String value = dataSnapshot.getValue(String.class);
                list.add(0, value);
                adapter.notifyDataSetChanged();

                NotificationCompat.Builder builder = new NotificationCompat.Builder(getActivity());
                builder.setSmallIcon(R.drawable.common_google_signin_btn_icon_light);
                builder.setContentTitle("Attend Story");
                builder.setContentText("Someone enter the room");
                Intent intent = getActivity().getIntent();

                PendingIntent pendingIntent = PendingIntent.getActivity(getActivity().getApplicationContext(), (int) System.currentTimeMillis(), intent, PendingIntent.FLAG_UPDATE_CURRENT);
                builder.setContentIntent(pendingIntent);
                builder.setDefaults(NotificationCompat.DEFAULT_VIBRATE);
                builder.setAutoCancel(true);
                NotificationManager NM = (NotificationManager) getActivity().getSystemService(Context.NOTIFICATION_SERVICE);
                NM.notify(0, builder.build());


            }

            @Override
            public void onChildChanged(com.google.firebase.database.DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(com.google.firebase.database.DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(com.google.firebase.database.DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



        LinearLayoutManager manager = new LinearLayoutManager(getActivity().getBaseContext());
        rvView.setHasFixedSize(true);
        rvView.setLayoutManager(manager);
        rvView.setAdapter(adapter);
    }
}