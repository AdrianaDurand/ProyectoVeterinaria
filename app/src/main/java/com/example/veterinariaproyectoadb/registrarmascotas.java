package com.example.veterinariaproyectoadb;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class registrarmascotas extends AppCompatActivity {
    EditText etNombreMascota, etRazaMascota, etTipoMascota, etPesoMascota, etAlturaMascota, etColorMascota, etEdadMascota;
    Button btRegistrarMascota, btAbrirBusquedaMascota, btAbrirListaMascota;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrarmascotas);

        loadUI();

        btRegistrarMascota.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validarCampos();
            }
        });

        btAbrirBusquedaMascota.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), buscarmascota.class));
            }
        });
    }

    private void validarCampos() {
        String nombremascota, raza, tipo, color;
        int peso, altura, edad;

        nombremascota = etNombreMascota.getText().toString();
        color = etColorMascota.getText().toString();
        raza = etRazaMascota.getText().toString();
        tipo = etTipoMascota.getText().toString();
        edad = (etEdadMascota.getText().toString().trim().isEmpty()) ? 0 : Integer.parseInt(etEdadMascota.getText().toString());
        peso = (etPesoMascota.getText().toString().trim().isEmpty()) ? 0 : Integer.parseInt(etPesoMascota.getText().toString());
        altura = (etAlturaMascota.getText().toString().trim().isEmpty()) ? 0 : Integer.parseInt(etAlturaMascota.getText().toString());


        if (nombremascota.isEmpty() || raza.isEmpty() || tipo.isEmpty() || color.isEmpty() || peso == 0 || altura == 0 || edad == 0) {
            notificar("Complete el formulario");
            etNombreMascota.requestFocus();
        } else {
            preguntar();
        }

    }

    private void preguntar() {
        AlertDialog.Builder dialogo = new AlertDialog.Builder(this);
        dialogo.setTitle("VETERINARIA");
        dialogo.setMessage("¿Está seguro de registrar a esta Mascota?");
        dialogo.setCancelable(false);

        dialogo.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                registrarMascota();}
        });

        dialogo.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        dialogo.show();
    }

    private void registrarMascota(){

        ConexionSQLiteHelper conexion = new ConexionSQLiteHelper(this,"dbveterinaria", null, 1);
        SQLiteDatabase db = conexion.getReadableDatabase();


        ContentValues parametros = new ContentValues();
        parametros.put("nombremascota", etNombreMascota.getText().toString());
        parametros.put("color", etColorMascota.getText().toString());
        parametros.put("edad", etEdadMascota.getText().toString());
        parametros.put("raza", etRazaMascota.getText().toString());
        parametros.put("tipo", etTipoMascota.getText().toString());
        parametros.put("peso", etPesoMascota.getText().toString());
        parametros.put("altura", etAlturaMascota.getText().toString());


        long idobtenido = db.insert("mascotas", "idmascota", parametros);
        notificar("Registro guardado - " + String.valueOf(idobtenido));
        reiniciar();
        etNombreMascota.requestFocus();
    }

    private void reiniciar(){
        etNombreMascota.setText(null);
        etColorMascota.setText(null);
        etEdadMascota.setText(null);
        etRazaMascota.setText(null);
        etTipoMascota.setText(null);
        etPesoMascota.setText(null);
        etAlturaMascota.setText(null);
    }

    private void notificar(String mensaje){
        Toast.makeText(this, mensaje, Toast.LENGTH_LONG).show();
    }

    private void loadUI() {
        etNombreMascota = findViewById(R.id.etNombreMascota);
        etColorMascota = findViewById(R.id.etColorMascota);
        etEdadMascota = findViewById(R.id.etEdadMascota);
        etRazaMascota = findViewById(R.id.etRazaMascota);
        etTipoMascota = findViewById(R.id.etTipoMascota);
        etPesoMascota = findViewById(R.id.etPesoMascota);
        etAlturaMascota = findViewById(R.id.etAlturaMascota);

        btRegistrarMascota = findViewById(R.id.btRegistrarMascota);
        btAbrirBusquedaMascota = findViewById(R.id.btAbrirBusquedaMascota);

    }


    }