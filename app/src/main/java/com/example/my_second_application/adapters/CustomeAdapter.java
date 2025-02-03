package com.example.my_second_application.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.my_second_application.R;
import com.example.my_second_application.models.Data;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class CustomeAdapter extends RecyclerView.Adapter<CustomeAdapter.myViewHolder> {

    private ArrayList<Data> arr;
    private DatabaseReference databaseReference;
    private String userEmail;
    private ValueEventListener firebaseListener;


    public CustomeAdapter(ArrayList<Data> arr) {
        this.arr = arr;

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            userEmail = user.getEmail().replace(".", "_");
            databaseReference = FirebaseDatabase.getInstance().getReference("Root").child("Baskets").child(userEmail);
            addRealTimeFirebaseListener();
        }
    }
    private void addRealTimeFirebaseListener() {
        firebaseListener = new ValueEventListener() {
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
                notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle potential errors
            }
        };
        databaseReference.addValueEventListener(firebaseListener);
    }

    // Don't forget to remove the listener when the adapter is destroyed
    public void cleanup() {
        if (databaseReference != null && firebaseListener != null) {
            databaseReference.removeEventListener(firebaseListener);
        }
    }

    public class myViewHolder extends RecyclerView.ViewHolder {
        TextView productName;
        ImageView imageViewItem;
        TextView amountTextView;
        Button plusButton;
        Button minusButton;

        public myViewHolder(View itemView) {
            super(itemView);
            productName = itemView.findViewById(R.id.textCheckoutName);
            imageViewItem = itemView.findViewById(R.id.imageCheckout);
            amountTextView = itemView.findViewById(R.id.textCheckoutAmount);
            plusButton = itemView.findViewById(R.id.add_btn);
            minusButton = itemView.findViewById(R.id.remove_btn);
        }
    }

    @NonNull
    @Override
    public CustomeAdapter.myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview, parent, false);
        return new myViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomeAdapter.myViewHolder holder, int position) {
        Data item = arr.get(position);
        holder.productName.setText(item.getName());
        holder.imageViewItem.setImageResource(item.getImage());
        holder.amountTextView.setText(String.valueOf(item.getAmount()));

        holder.plusButton.setOnClickListener(v -> updateItemInFirebase(item, 1, holder));
        holder.minusButton.setOnClickListener(v -> updateItemInFirebase(item, -1, holder));
    }

    private void updateItemInFirebase(Data item, int change, myViewHolder holder) {
        int newAmount = Math.max(item.getAmount() + change, 0);
        item.setAmount(newAmount);
        holder.amountTextView.setText(String.valueOf(newAmount));

        if (newAmount > 0) {
            HashMap<String, Object> updateMap = new HashMap<>();
            updateMap.put("name", item.getName()); // Ensure name is saved
            updateMap.put("amount", newAmount);
            updateMap.put("image", item.getImage());
            databaseReference.child(item.getName()).setValue(updateMap);
        } else {
            databaseReference.child(item.getName()).removeValue();
        }
    }

    @Override
    public int getItemCount() {
        return arr.size();
    }
}
