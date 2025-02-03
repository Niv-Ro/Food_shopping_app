package com.example.my_second_application.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.my_second_application.R;
import com.example.my_second_application.models.CheckoutData;

import java.util.ArrayList;

public class CheckoutAdapter extends RecyclerView.Adapter<CheckoutAdapter.ViewHolder> {

    private ArrayList<CheckoutData> checkoutList;

    public CheckoutAdapter(ArrayList<CheckoutData> checkoutList) {
        this.checkoutList = checkoutList;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView productName;
        TextView amountTextView;
        ImageView imageViewItem;

        public ViewHolder(View itemView) {
            super(itemView);
            productName = itemView.findViewById(R.id.textCheckoutName);
            amountTextView = itemView.findViewById(R.id.textCheckoutAmount);
            imageViewItem = itemView.findViewById(R.id.imageCheckout);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.checkout_cardview, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CheckoutData item = checkoutList.get(position);
        holder.productName.setText(item.getName());
        holder.amountTextView.setText("Amount: " + item.getAmount());
        holder.imageViewItem.setImageResource(item.getImage());
    }

    @Override
    public int getItemCount() {
        return checkoutList.size();
    }
}
