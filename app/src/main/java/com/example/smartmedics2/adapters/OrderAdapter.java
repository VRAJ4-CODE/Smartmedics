package com.example.smartmedics2.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.smartmedics2.R;
import com.example.smartmedics2.model.OrderModel;

import java.util.List;


public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.ViewHolder> {

    private Context context;
    private List<OrderModel> list;

    public OrderAdapter(Context context,List<OrderModel> list) {
        this.context = context;
        this.list = list;
    }




    @NonNull
    @Override
    public OrderAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder((LayoutInflater.from(parent.getContext()).inflate(R.layout.order_products,parent,false)));
    }

    @Override
    public void onBindViewHolder(@NonNull OrderAdapter.ViewHolder holder, int position) {

        Glide.with(context).load(list.get(position).getImg_url()).into(holder.orderImg);
        holder.orderName.setText(list.get(position).getName());
        holder.orderPrice.setText(list.get(position).getPrice());
        holder.orderDate.setText(list.get(position).getDate());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView orderImg;
        TextView orderName,orderPrice,orderDate;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            orderImg = itemView.findViewById(R.id.order_image);
            orderName = itemView.findViewById(R.id.order_title);
            orderPrice = itemView.findViewById(R.id.order_price);
            orderDate = itemView.findViewById(R.id.dateTime);
        }
    }
}
