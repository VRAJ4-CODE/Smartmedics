package com.example.smartmedics2.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.smartmedics2.R;
import com.example.smartmedics2.activities.CartActivity;
import com.example.smartmedics2.model.CartItemModel;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class MyCartAdapter extends RecyclerView.Adapter<MyCartAdapter.ViewHolder> {

    private Context context;
    private List<CartItemModel> list;
    private FirebaseFirestore firestore;
    private String currentUserUID;

    public MyCartAdapter(Context context, List<CartItemModel> list, FirebaseFirestore firestore, String currentUserUID) {
        this.context = context;
        this.list = list;
        this.firestore = firestore;
        this.currentUserUID = currentUserUID;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_item_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Glide.with(context).load(list.get(position).getImg_url()).into(holder.cartImg);
        holder.cartName.setText(list.get(position).getName());
        holder.cartPrice.setText(list.get(position).getPrice());
        holder.cartQuantity.setText(list.get(position).getQuantity());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView cartImg;
        TextView cartName, cartPrice, cartQuantity, removeItem;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cartImg = itemView.findViewById(R.id.cart_image);
            cartName = itemView.findViewById(R.id.cart_title);
            cartPrice = itemView.findViewById(R.id.cart_price);
            cartQuantity = itemView.findViewById(R.id.cart_quantity);
            removeItem = itemView.findViewById(R.id.remove);

            removeItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        String documentId = list.get(position).getDocumentId();

                        firestore.collection("AddToCart")
                                .document(currentUserUID)
                                .collection("User")
                                .document(documentId)
                                .delete()
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        // Item deleted successfully
                                        Toast.makeText(context, "Item removed from cart", Toast.LENGTH_SHORT).show();
                                        ((CartActivity) context).reloadCartItems(); // Reload the cart items
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        // Failed to delete item
                                        Toast.makeText(context, "Failed to remove item from cart", Toast.LENGTH_SHORT).show();
                                    }
                                });
                    }
                }
            });
        }
    }
}
