package com.example.ruben.rfid_hampir;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ruben.rfid_hampir.model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * Created by Daniel on 1/8/2017.
 */

public class AdapterDatabase extends RecyclerView.Adapter<AdapterDatabase.MyViewHolder>{

    private ArrayList<User> list;

    public AdapterDatabase(ArrayList<User> list) {
        this.list = list;
    }

    @Override
    public AdapterDatabase.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_db, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AdapterDatabase.MyViewHolder holder, int position) {
        User s = list.get(position);
        holder.bind(s);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView txtNrp;
        private TextView txtNama;

        private FirebaseDatabase mDatabase;
        private DatabaseReference mRef;

        public MyViewHolder(View itemView) {
            super(itemView);

            mDatabase = FirebaseDatabase.getInstance();


            txtNama = (TextView) itemView.findViewById(R.id.dbnama);
            txtNrp = (TextView) itemView.findViewById(R.id.dbnrp);
        }

        public void bind(final User s) {
            txtNama.setText("Nama: "+s.getNama());
            txtNrp.setText("NRP: "+s.getNRP().toString());

        }
    }
}
