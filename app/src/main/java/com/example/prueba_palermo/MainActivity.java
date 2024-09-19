package com.example.prueba_palermo;

import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity {

    private Button btnPedido;
    private Button btnFactura;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnPedido = findViewById(R.id.btn_pedido);
        btnFactura = findViewById(R.id.btn_factura);


        btnFactura.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFragment("factura");
            }
        });

        btnPedido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFragment("pedido");
            }
        });
    }
    private void openFragment(String type) {
        Intent intent = new Intent(this, FragmentsActivity.class);
        intent.putExtra("type", type);
        startActivity(intent);
    }
}