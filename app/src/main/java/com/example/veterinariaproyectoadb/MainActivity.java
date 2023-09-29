package com.example.veterinariaproyectoadb;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button btAccederDuenos, btAccederMascotas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loadUI();

        btAccederDuenos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), registrarduenos.class));
            }
        });

        btAccederMascotas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), registrarmascotas.class));
            }
        });

    }

    private void loadUI(){
        btAccederDuenos = findViewById(R.id.btAccederDuenos);
        btAccederMascotas = findViewById(R.id.btAccederMascotas);
    }
}