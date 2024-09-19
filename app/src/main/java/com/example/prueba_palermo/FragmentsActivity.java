package com.example.prueba_palermo;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.prueba_palermo.fragments.FacturaFragment;
import com.example.prueba_palermo.fragments.PedidoFragment;

public class FragmentsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragments);

        Intent intent = getIntent();
        String type = intent.getStringExtra("type");

        if (type != null) {
            loadFragment(type);
        }
    }

    private void loadFragment(String type) {
        Fragment fragment;

        if ("factura".equals(type)) {
            fragment = new FacturaFragment();
        } else if ("pedido".equals(type)) {
            fragment = new PedidoFragment();
        } else {
            throw new IllegalArgumentException("no se encuentra: " + type);
        }

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fragment);
        fragmentTransaction.commit();
    }
}