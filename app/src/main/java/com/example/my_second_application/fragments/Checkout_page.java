package com.example.my_second_application.fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.my_second_application.R;
import com.example.my_second_application.adapters.CheckoutAdapter;
import com.example.my_second_application.models.CheckoutData;
import com.google.android.material.snackbar.Snackbar;
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
 * Use the {@link Checkout_page#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Checkout_page extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private RecyclerView recyclerView;
    private CheckoutAdapter adapter;
    private ArrayList<CheckoutData> checkoutList;
    private DatabaseReference databaseReference;
    private String userEmail;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Checkout_page() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Checkout.
     */
    // TODO: Rename and change types and number of parameters
    public static Checkout_page newInstance(String param1, String param2) {
        Checkout_page fragment = new Checkout_page();
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
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_checkout, container, false);

        recyclerView = view.findViewById(R.id.rvcon_checkout);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        checkoutList = new ArrayList<>();
        adapter = new CheckoutAdapter(checkoutList);
        recyclerView.setAdapter(adapter);
        Button buy_btn = view.findViewById(R.id.buy_Btn);
        Button back_to_list_btn = view.findViewById(R.id.back_to_list_Btn);

        back_to_list_btn.setOnClickListener(v -> {
            Navigation.findNavController(view).navigate(R.id.action_checkout_to_fragmentTwo);
        });

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            userEmail = user.getEmail().replace(".", "_");
            databaseReference = FirebaseDatabase.getInstance().getReference("Root").child("Baskets").child(userEmail);
            fetchCheckoutData();
        } else {
            Toast.makeText(getContext(), "User not logged in", Toast.LENGTH_SHORT).show();
        }

        buy_btn.setOnClickListener(v -> {
            if(!checkoutList.isEmpty()) {
                databaseReference.removeValue().addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Snackbar snackbar = Snackbar.make(requireView(),
                                "Purchase Completed!",
                                Snackbar.LENGTH_LONG);
                        snackbar.setBackgroundTint(Color.parseColor("#322D29"));
                        snackbar.setTextColor(Color.WHITE);
                        snackbar.show();
                        Navigation.findNavController(view).navigateUp();
                    }
                });
            }
            else {
                Snackbar snackbar = Snackbar.make(requireView(),
                        "Nothing in the cart!",
                        Snackbar.LENGTH_LONG);
                snackbar.setBackgroundTint(Color.parseColor("#322D29"));
                snackbar.setTextColor(Color.WHITE);
                snackbar.show();
            }
        });
        return view;
    }

    private void fetchCheckoutData() {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                checkoutList.clear();
                for (DataSnapshot itemSnapshot : snapshot.getChildren()) {
                    CheckoutData item = itemSnapshot.getValue(CheckoutData.class);
                    checkoutList.add(item);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), "Failed to load cart", Toast.LENGTH_SHORT).show();
            }
        });
    }
}