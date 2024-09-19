package com.example.prueba_palermo.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.prueba_palermo.R;
import com.example.prueba_palermo.adapter.AdapterGeneric;
import com.example.prueba_palermo.viewModel.ItemViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;


public class FacturaFragment extends Fragment {

    private RecyclerView recyclerView;
    private TextView tvTotal, txt_agregar_items;
    private ItemViewModel viewModel;
    private AdapterGeneric adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.fragment_factura, container, false);

        txt_agregar_items = view.findViewById(R.id.txt_agregar_item);
        tvTotal = view.findViewById(R.id.txt_Total);
        recyclerView = view.findViewById(R.id.recyclerViewItems);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        ItemViewModel.ItemViewModelFactory factory = new ItemViewModel.ItemViewModelFactory(getActivity().getApplication());
        viewModel = new ViewModelProvider(this, factory).get(ItemViewModel.class);

        RecyclerView recyclerView = view.findViewById(R.id.recyclerViewItems);
        adapter = new AdapterGeneric();
        recyclerView.setAdapter(adapter);

        viewModel.getAllItems().observe(getViewLifecycleOwner(), items -> {
            adapter.submitList(items);
        });

        viewModel.getTotal().observe(getViewLifecycleOwner(), total -> {
            tvTotal.setText(String.format(" %d", total));
        });

        txt_agregar_items.setOnClickListener(v ->{
            Fragment secondFragment = new AgregarItemsFragment();
            FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
            transaction.replace(R.id.fragment_container, secondFragment);
            transaction.addToBackStack(null);
            transaction.commit();
        });

        FloatingActionButton fabAddItem = view.findViewById(R.id.fabAddItem);
        fabAddItem.setOnClickListener(v -> {
            //Funcionalidad pendiente
        });

        return view;
    }
}