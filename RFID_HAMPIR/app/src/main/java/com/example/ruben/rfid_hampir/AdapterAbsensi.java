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
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import static java.lang.Integer.min;
import static java.lang.Integer.parseInt;

/**
 * Created by Ruben on 1/12/2017.
 */

public class AdapterAbsensi extends RecyclerView.Adapter<AdapterAbsensi.MyViewHolder> {
    private ArrayList<String> list;

    public AdapterAbsensi(ArrayList<String>list) {this.list = list;}

    @Override
    public AdapterAbsensi.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_absensi, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AdapterAbsensi.MyViewHolder holder, int position) {
        String s = list.get(position);
        holder.bind(s);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        private TextView txtNrp;
        private TextView txtNama;
        private TextView txtJam;
        private TextView txtAbsen;

        private FirebaseDatabase mDatabase;
        private DatabaseReference mRef;

        public MyViewHolder(View itemView){
            super(itemView);

            mDatabase = FirebaseDatabase.getInstance();


            txtNama = (TextView) itemView.findViewById(R.id.anama);
            txtNrp = (TextView) itemView.findViewById(R.id.anrp);
            txtJam = (TextView) itemView.findViewById(R.id.ajam);
            txtAbsen = (TextView) itemView.findViewById(R.id.aabsen);
        }

        public void bind (final String s){
            final String ch[] = s.split("#");
            mRef = mDatabase.getReference("Users").child(ch[0]);
            mRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    try {
                        Log.d("TAGGING","TRY");
                        User u = dataSnapshot.getValue(User.class);
                        txtNama.setText(u.getNama());
                        //mDatabase.getReference("Absensi").child("Nama").setValue(u.getNama()); //NEW
                        txtNrp.setText("NRP: " + u.getNRP().toString());
                        //mDatabase.getReference("Absensi").child("NRP").setValue(u.getNRP().toString()); //NEW
                        txtJam.setText("Waktu : " + ch[1] +" "+ ch[2]);
                        //mDatabase.getReference("Absensi").child("Waktu").setValue(ch[1]+" "+ch[2]); //NEW
                        String hari = ch[3];

                        if (hari.equals(global.gHari))
                        {   Log.d("TAGGING","HARI");
                            String jam[] = ch[2].split(":");
                            Integer hour = parseInt(jam[0]);
                            Integer minute = parseInt(jam[1]);
                            Integer second = parseInt(jam[2]);

                            if((hour == Integer.parseInt(global.gJam)) && ((minute <= (Integer.parseInt(global.gMenit))+15)&&(minute >= (Integer.parseInt(global.gMenit)) - 5)))
                            {
                                Log.d("TAGGING","prak");
                                txtAbsen.setText("Praktikan : HADIR");
                                //mDatabase.getReference("Absensi").child("Praktikan").setValue("HADIR"); //NEW
                            }
                            else
                            {
                                txtAbsen.setText("Praktikan : TERLAMBAT");
                                //mDatabase.getReference("Absensi").child("Praktikan").setValue("TERLAMBAT"); //NEW
                            }
                        }
                        else
                        {
                            txtAbsen.setText("Praktikan : TIDAK HADIR");
                            //mDatabase.getReference("Absensi").child("Praktikan").setValue("TIDAK HADIR"); //NEW
                        }

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
