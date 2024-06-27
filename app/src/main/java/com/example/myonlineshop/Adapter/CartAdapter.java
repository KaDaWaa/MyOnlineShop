package com.example.myonlineshop.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.myonlineshop.Domain.ItemsDomain;
import com.example.myonlineshop.Helper.ChangeNumberItemsListener;
import com.example.myonlineshop.Helper.ManagmentCart;
import com.example.myonlineshop.databinding.ViewholderCartitemBinding;

import java.util.ArrayList;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {
    ArrayList<ItemsDomain> listItemSelected;
    ChangeNumberItemsListener changeNumberItemsListener;
    private ManagmentCart managmentCart;

    public CartAdapter(ArrayList<ItemsDomain> listItemSelected, Context context, ChangeNumberItemsListener changeNumberItemsListener) {
        this.listItemSelected = listItemSelected;
        this.changeNumberItemsListener = changeNumberItemsListener;
        managmentCart=new ManagmentCart(context);
    }

    @NonNull
    @Override
    public CartAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ViewholderCartitemBinding binding=ViewholderCartitemBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);

        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull CartAdapter.ViewHolder holder, int position) {
        holder.binding.cartItemTitleTV.setText(listItemSelected.get(position).getTitle());
        holder.binding.subtotalCartItemTV.setText("$"+listItemSelected.get(position).getPrice());
        holder.binding.totalCartItemTV.setText("$"+Math.round((listItemSelected.get(position).getNumberinCart()*listItemSelected.get(position).getPrice())));
        holder.binding.amountInCartTV.setText(String.valueOf(listItemSelected.get(position).getNumberinCart()));

        Glide.with(holder.itemView.getContext())
                .load(listItemSelected.get(position).getPicUrl().get(0))
                .apply(RequestOptions.circleCropTransform())
                .into(holder.binding.cartItemPic);

        holder.binding.plusCartButton.setOnClickListener(v -> {
            managmentCart.plusItem(listItemSelected, position, () -> {
                notifyDataSetChanged();
                changeNumberItemsListener.changed();
            });
        });

        holder.binding.minusCartButton.setOnClickListener(v -> {
            managmentCart.minusItem(listItemSelected,position,()->{
                notifyDataSetChanged();
                changeNumberItemsListener.changed();
            });
        });

    }

    @Override
    public int getItemCount() {
        return listItemSelected.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ViewholderCartitemBinding binding;
        public ViewHolder(ViewholderCartitemBinding binding) {
            super(binding.getRoot());
            this.binding=binding;
        }
    }
}
