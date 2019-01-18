package com.example.ruben.rfid_hampir;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ruben.rfid_hampir.model.User;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import static com.example.ruben.rfid_hampir.global.namanotif;

/**
 * Created by Daniel on 1/4/2017.
 */

public class AdapterHistory extends RecyclerView.Adapter<AdapterHistory.MyViewHolder>{

    private ArrayList<String> list;

    public AdapterHistory(ArrayList<String> list) {
        this.list = list;
    }

    @Override
    public AdapterHistory.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_history, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AdapterHistory.MyViewHolder holder, int position) {
        String s = list.get(position);
        holder.bind(s);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView txtNrp;
        private TextView txtNama;
        private TextView txtJam;

        private FirebaseDatabase mDatabase;
        private DatabaseReference mRef;


        public MyViewHolder(View itemView) {
            super(itemView);

            mDatabase = FirebaseDatabase.getInstance();


            txtNama = (TextView) itemView.findViewById(R.id.hnama);
            txtNrp = (TextView) itemView.findViewById(R.id.hnrp);
            txtJam = (TextView) itemView.findViewById(R.id.hjam);
        }

        public void bind(final String s) {
            final String ch[] = s.split("#");
            mRef = mDatabase.getReference("Users").child(ch[0]);
            mRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    try {
                        User u = dataSnapshot.getValue(User.class);
                        txtNama.setText(u.getNama());
                        txtNrp.setText("NRP: " + u.getNRP().toString());
                        txtJam.setText("Waktu : " + ch[1] +" "+ ch[2]);


                    } catch (Exception e) {
                        txtNama.setText("Unknown");
                        txtNrp.setText("RFID: " + ch[0]);
                        txtJam.setText("Waktu : " + ch[1] +" "+ ch[2]);

                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });


        }
    }
}
