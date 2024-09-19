package com.example.prueba_palermo.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;
import com.example.prueba_palermo.R;
import com.example.prueba_palermo.model.Item;


public class AdapterGeneric extends ListAdapter<Item, AdapterGeneric.ItemViewHolder> {

    public AdapterGeneric() {
        super(DIFF_CALLBACK);
    }

    private static final DiffUtil.ItemCallback<Item> DIFF_CALLBACK = new DiffUtil.ItemCallback<Item>() {
        @Override
        public boolean areItemsTheSame(@NonNull Item oldItem, @NonNull Item newItem) {
            return oldItem.getName() == newItem.getName();
        }

        @Override
        public boolean areContentsTheSame(@NonNull Item oldItem, @NonNull Item newItem) {
            return oldItem.getName().equals(newItem.getName())
                    && oldItem.getQuantity() == newItem.getQuantity()
                    && oldItem.getPrice() == newItem.getPrice()
                    && oldItem.getSubtotal() == newItem.getSubtotal();
        }
    };

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_card_generic, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        Item item = getItem(position);
        holder.bind(item);
    }


    static class ItemViewHolder extends RecyclerView.ViewHolder {

        private final TextView nameTextView;
        private final TextView quantityTextView;
        private final TextView priceTextView;
        private final TextView subtotalTextView;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.tvItemName);
            quantityTextView = itemView.findViewById(R.id.tvItemQuantity);
            priceTextView = itemView.findViewById(R.id.tvItemPrice);
            subtotalTextView = itemView.findViewById(R.id.tvItemSubtotal);
        }

        public void bind(Item item) {
            nameTextView.setText(item.getName());
            quantityTextView.setText(String.valueOf(item.getQuantity()));
            priceTextView.setText("Precio: " + item.getPrice() + " gs");
            subtotalTextView.setText("Subtotal: " + item.getSubtotal() + " gs");
        }
    }


}
