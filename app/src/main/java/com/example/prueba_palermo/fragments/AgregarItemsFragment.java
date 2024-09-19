package com.example.prueba_palermo.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import com.example.prueba_palermo.R;
import com.example.prueba_palermo.model.Item;
import com.example.prueba_palermo.viewModel.ItemViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class AgregarItemsFragment extends Fragment {

    private ItemViewModel viewModel;
    int yerbaQuantity = 0;
    int cigarilloQuantity = 0;
    int yerbaPrice = 5000;
    int cigarilloPrice = 4500;
    int yerbaSubtotal = yerbaPrice * yerbaQuantity;
    int cigarilloSubtotal = cigarilloPrice * cigarilloQuantity;

    TextView tvCigarilloPrice;
    TextView tvCigarilloSubtotal;
    EditText etCigarilloQuantity;

    TextView tvYerbaPrice ;
    TextView tvYerbaSubtotal;
    EditText etYerbaQuantity;
    ImageButton btnCigarilloIncrease, btnCigarilloDecrease,btnYerbaIncrease, btnYerbaDecrease;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_agregar_items, container, false);

        ItemViewModel.ItemViewModelFactory factory = new ItemViewModel.ItemViewModelFactory(getActivity().getApplication());
        viewModel = new ViewModelProvider(this, factory).get(ItemViewModel.class);
        tvYerbaPrice = view.findViewById(R.id.tvYerba250gPrice);
        tvYerbaSubtotal = view.findViewById(R.id.tvYerba250gSubtotal);
        etYerbaQuantity = view.findViewById(R.id.etYerba250gQuantity);

        btnYerbaIncrease = view.findViewById(R.id.btnYerba250gIncrease);
        btnYerbaDecrease = view.findViewById(R.id.btnYerba250gDecrease);

        tvCigarilloPrice = view.findViewById(R.id.tvCigarillo10BoxPrice);
        tvCigarilloSubtotal = view.findViewById(R.id.tvCigarillo10BoxSubtotal);
        etCigarilloQuantity = view.findViewById(R.id.etCigarillo10BoxQuantity);

        btnCigarilloIncrease = view.findViewById(R.id.btnCigarillo10BoxIncrease);
        btnCigarilloDecrease = view.findViewById(R.id.btnCigarillo10BoxDecrease);

        FloatingActionButton fabAddItem = view.findViewById(R.id.fabAddItem);

        viewModel.getYerba250g().observe(getViewLifecycleOwner(), item -> {
            etYerbaQuantity.setText(String.valueOf(item.getQuantity()));
            tvYerbaSubtotal.setText("Subtotal: " + item.getSubtotal() + " gs");
        });

        viewModel.getCigarillo10Box().observe(getViewLifecycleOwner(), item -> {
            etCigarilloQuantity.setText(String.valueOf(item.getQuantity()));
            tvCigarilloSubtotal.setText("Subtotal: " + item.getSubtotal() + " gs");
        });

        btnYerbaIncrease.setOnClickListener(v -> viewModel.increaseQuantity("Yerba 250g"));
        btnYerbaDecrease.setOnClickListener(v -> viewModel.decreaseQuantity("Yerba 250g"));

        btnCigarilloIncrease.setOnClickListener(v -> viewModel.increaseQuantity("Cigarillo 10Box"));
        btnCigarilloDecrease.setOnClickListener(v -> viewModel.decreaseQuantity("Cigarillo 10Box"));


        btnYerbaIncrease.setOnClickListener(v -> {
            yerbaQuantity++;
            updateYerbaSubtotal();

        });

        btnYerbaDecrease.setOnClickListener(v -> {
            if (yerbaQuantity > 1) {
                yerbaQuantity--;
                updateYerbaSubtotal();
            }
        });

        btnCigarilloIncrease.setOnClickListener(v -> {
            cigarilloQuantity++;
            updateCigarilloSubtotal();
        });

        btnCigarilloDecrease.setOnClickListener(v -> {
            if (cigarilloQuantity > 1) {
                cigarilloQuantity--;
                updateCigarilloSubtotal();
            }
        });

        fabAddItem.setOnClickListener(v -> {

            saveYerbaItem();
            saveCigarilloItem();

            requireActivity().onBackPressed();
        });

        return view;
    }
    private void updateYerbaSubtotal() {
        if (yerbaQuantity >= 10) {
            yerbaPrice = 4500;
        } else {
            yerbaPrice = 5000;
        }
        yerbaSubtotal = yerbaPrice * yerbaQuantity;
        etYerbaQuantity.setText(String.valueOf(yerbaQuantity));
        tvYerbaSubtotal.setText(String.valueOf("Sub-Total:"+yerbaSubtotal));
    }

    private void updateCigarilloSubtotal() {
        if (cigarilloQuantity >= 10) {
            cigarilloPrice = 4200;
        } else {
            cigarilloPrice = 4500;
        }
        cigarilloSubtotal = cigarilloPrice * cigarilloQuantity;
        etCigarilloQuantity.setText(String.valueOf(cigarilloQuantity));
        tvCigarilloSubtotal.setText(String.valueOf("Sub-Total:"+cigarilloSubtotal));
    }

    private void saveYerbaItem() {
        Item yerbaItem = new Item("Yerba 250g", yerbaQuantity, yerbaPrice, yerbaSubtotal);
        viewModel.insertOrUpdate(yerbaItem);
    }

    private void saveCigarilloItem() {
        Item cigarilloItem = new Item("Cigarillo 10Box", cigarilloQuantity, cigarilloPrice, cigarilloSubtotal);
        viewModel.insertOrUpdate(cigarilloItem);
    }
}
