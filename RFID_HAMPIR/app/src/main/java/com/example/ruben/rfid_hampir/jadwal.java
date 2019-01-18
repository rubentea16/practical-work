package com.example.ruben.rfid_hampir;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import com.firebase.client.Firebase;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Ruben on 5/27/2017.
 */

public class jadwal extends Fragment{
    public jadwal(){}

    private SharedPreferences sp;
    SharedPreferences.Editor editor;
    Context context = getActivity();
    Spinner spin;
    TimePicker timePicker;
    Button button;
    TextView txt;
    TextView schedule1;
    TextView schedule2;
    ArrayList<String> arrayHari = new ArrayList<String>();
    private FirebaseDatabase mDatabase;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getActivity().setTitle("Jadwal");
        return inflater.inflate(R.layout.jadwal , container ,false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        arrayHari.add("Senin");
        arrayHari.add("Selasa");
        arrayHari.add("Rabu");
        arrayHari.add("Kamis");
        arrayHari.add("Jumat");
        arrayHari.add("Sabtu");
        arrayHari.add("Minggu");
        spin = (Spinner) getActivity().findViewById(R.id.spinner);
        button = (Button) getActivity().findViewById(R.id.button);
        txt = (TextView) getActivity().findViewById(R.id.textView);
        schedule1 = (TextView) getActivity().findViewById(R.id.textView2);
        schedule2 = (TextView) getActivity().findViewById(R.id.textView3);
        ArrayAdapter<String> adapterHari = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item,arrayHari);
        adapterHari.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin.setAdapter(adapterHari);
        schedule1.setText("Jadwal : "+global.gHari);
        schedule2.setText("Pukul :"+global.gJam+":"+global.gMenit);

        Firebase.setAndroidContext(getActivity());
        mDatabase = FirebaseDatabase.getInstance();
        final DatabaseReference myRef = mDatabase.getReference("Jadwal");

        timePicker = (TimePicker) getActivity().findViewById(R.id.timePicker);
//        sp = context.getSharedPreferences(global.LocalData, Context.MODE_PRIVATE);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(v.getContext());
                alertDialogBuilder.setMessage("Are you sure, you wanted to save this Schedule ?");
                alertDialogBuilder.setPositiveButton("Yes",new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        SharedPreferences sp = getActivity().getPreferences(Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sp.edit();
                        global.gHari = spin.getSelectedItem().toString();
                        timePicker = (TimePicker) getActivity().findViewById(R.id.timePicker);
                        global.gJam = Integer.toString(timePicker.getHour());
                        global.gMenit = Integer.toString(timePicker.getMinute());
                        String gabung = global.gHari+"#"+global.gJam+":"+global.gMenit;
                        myRef.child("Praktikum").setValue(gabung);
                        schedule1.setText("Jadwal : "+global.gHari);
                        schedule2.setText("Pukul :"+global.gJam+":"+global.gMenit);
                    }
                });
                alertDialogBuilder.setNegativeButton("No",new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
            }
        });


    }
}
