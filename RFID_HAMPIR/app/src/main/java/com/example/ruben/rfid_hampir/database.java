package com.example.ruben.rfid_hampir;

        import android.app.Fragment;
        import android.os.Bundle;
        import android.support.annotation.Nullable;
        import android.support.v7.widget.LinearLayoutManager;
        import android.support.v7.widget.RecyclerView;
        import android.util.Log;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.RelativeLayout;

        import com.example.ruben.rfid_hampir.model.User;
        import com.firebase.client.Firebase;
        import com.google.firebase.database.ChildEventListener;
        import com.google.firebase.database.DataSnapshot;
        import com.google.firebase.database.DatabaseError;
        import com.google.firebase.database.DatabaseReference;
        import com.google.firebase.database.FirebaseDatabase;
        import com.google.firebase.database.Query;

        import java.util.ArrayList;

public class database extends Fragment{
    public database(){}


    private FirebaseDatabase mDatabase;

    RecyclerView rvView;
    ArrayList<User> list = new ArrayList<>();
    AdapterDatabase adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getActivity().setTitle("Database");
        return inflater.inflate(R.layout.database , container ,false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Firebase.setAndroidContext(getActivity());
        rvView = (RecyclerView) getActivity().findViewById(R.id.rvDatabase);
        adapter = new AdapterDatabase(list);

        mDatabase = FirebaseDatabase.getInstance();

        DatabaseReference myRef = mDatabase.getReference("Users");

        myRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                User u = dataSnapshot.getValue(User.class);
                list.add(0, u);
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

    }
}
