package com.example.my_second_application.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.my_second_application.R;
import com.example.my_second_application.adapters.CustomeAdapter;
import com.example.my_second_application.classes.MyData;
import com.example.my_second_application.models.Data;
import com.example.my_second_application.models.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Logged_in_page#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Logged_in_page extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private ArrayList<Data> arr;
    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private CustomeAdapter customeAdapter;
    private DatabaseReference databaseReference;
    private DatabaseReference databaseReference_user;

    private FirebaseUser currentUser;

    public Logged_in_page() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentTwo.
     */
    // TODO: Rename and change types and number of parameters
    public static Logged_in_page newInstance(String param1, String param2) {
        Logged_in_page fragment = new Logged_in_page();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.logged_in_page, container, false);

        // Firebase setup
        currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) {
            String userEmail = currentUser.getEmail().replace(".", "_");
            databaseReference = FirebaseDatabase.getInstance().getReference("Root").child("Baskets").child(userEmail);
            databaseReference_user=FirebaseDatabase.getInstance().getReference("Root").child("Users").child(userEmail);
            recyclerView = view.findViewById(R.id.rvcon);
            layoutManager = new LinearLayoutManager(getActivity());
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setItemAnimator(new DefaultItemAnimator());

            arr = new ArrayList<>();

            // Populate initial items
            for (int i = 0; i < MyData.nameArray.length; i++) {
                Data dataItem = new Data(
                        MyData.nameArray[i],
                        MyData.drawableArray[i],
                        MyData.id_[i]
                );
                arr.add(dataItem);
            }

            customeAdapter = new CustomeAdapter(arr);
            recyclerView.setAdapter(customeAdapter);

            // Fetch real-time amounts from Firebase
            fetchItemAmountsFromFirebase();
        }

        Button checkout_btn = view.findViewById(R.id.checkout_Btn);
        checkout_btn.setOnClickListener(v -> {
            Navigation.findNavController(view).navigate(R.id.action_fragmentTwo_to_checkout);
        });
        TextView nameTextView = view.findViewById(R.id.name_textview);
        databaseReference_user.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String userName = snapshot.child("name").getValue(String.class);
                if (userName != null) {
                    nameTextView.setText("Hello "+userName);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Optional: handle error
            }
        });

        return view;
    }

    private void fetchItemAmountsFromFirebase() {
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (Data item : arr) {
                    DataSnapshot itemSnapshot = snapshot.child(item.getName());
                    if (itemSnapshot.exists()) {
                        Integer amount = itemSnapshot.child("amount").getValue(Integer.class);
                        if (amount != null) {
                            item.updateAmountFromFirebase(amount);
                        }
                    }
                }
                customeAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle potential errors
            }
        });
    }
}