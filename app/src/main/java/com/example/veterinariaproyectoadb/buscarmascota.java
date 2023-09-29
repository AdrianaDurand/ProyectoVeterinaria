package com.example.veterinariaproyectoadb;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class buscarmascota extends AppCompatActivity {

    ConexionSQLiteHelper conexion;

    EditText etIDMascotaBuscado, etNombreMascotaB, etBuscarColor, etBuscarEdad, etBuscarRaza, etBuscarTipo, etBuscarPeso, etBuscarAltura;
    Button btBuscarMascota, btReiniciarRM, btActualizarMascota, btAEliminarMascota;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buscarmascota);
        conexion = new ConexionSQLiteHelper(getApplicationContext(), "dbveterinaria", null, 1);
        loadUI();
        btBuscarMascota.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {buscarMascota();}
        });

        btActualizarMascota.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {validarCampos();}
        });

        btReiniciarRM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {reiniciarUI();}
        });
        btAEliminarMascota.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {preguntarEliminar();}
        });
    }

    private void notificar(String mensaje){
        Toast.makeText(this, mensaje, Toast.LENGTH_LONG).show();
    }

    private void preguntarEliminar() {
        AlertDialog.Builder dialogo = new AlertDialog.Builder(this);
        dialogo.setTitle("VETERINARIA");
        dialogo.setMessage("¿Estás seguro de eliminar el registro?");
        dialogo.setCancelable(false);

        dialogo.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                eliminarmascota();
            }
        });
        dialogo.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        dialogo.show();
    }

    private void eliminarmascota() {
    SQLiteDatabase db = conexion.getWritableDatabase();
    String[] campoCriterio = {etIDMascotaBuscado.getText().toString()};
    db.delete("mascotas", "idmascota=?", campoCriterio);
    db.close();
    reiniciarUI();
    notificar("Registro eliminado correctamente");
    etIDMascotaBuscado.requestFocus();
    }

    private void preguntarActualizar(){
        AlertDialog.Builder dialogo = new AlertDialog.Builder(this);
        dialogo.setTitle("VETERINARIA");
        dialogo.setMessage("¿Estás seguro de actualizar?");
        dialogo.setCancelable(false);

        dialogo.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                actualizarMascota();}
        });
        dialogo.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {}
        });
    }

    private void actualizarMascota(){
        SQLiteDatabase db = conexion.getWritableDatabase();
        String[] campoCriterio = {etIDMascotaBuscado.getText().toString()};
        ContentValues parametros = new ContentValues();

        //PARAMETROS
        parametros.put("nombremascota", etNombreMascotaB.getText().toString());
        parametros.put("color", etBuscarColor.getText().toString());
        parametros.put("edad", etBuscarEdad.getText().toString());
        parametros.put("raza", etBuscarRaza.getText().toString());
        parametros.put("tipo", etBuscarTipo.getText().toString());
        parametros.put("peso", etBuscarPeso.getText().toString());
        parametros.put("altura", etBuscarAltura.getText().toString());


        db.update("mascotas", parametros, "idmascota=?", campoCriterio);
        db.close();

        //REINICIAR
        notificar("Actualizado correctamente");
        reiniciarUI();
        etIDMascotaBuscado.requestFocus();
    }

    private void reiniciarUI(){
        etNombreMascotaB.setText(null);
        etBuscarColor.setText(null);
        etBuscarEdad.setText(null);
        etBuscarRaza.setText(null);
        etBuscarTipo.setText(null);
        etBuscarPeso.setText(null);
        etBuscarAltura.setText(null);

    }

    private void validarCampos() {
        String nombremascota, raza, tipo, color;
        int peso, altura, edad;

        nombremascota = etNombreMascotaB.getText().toString();
        color = etBuscarColor.getText().toString();
        raza = etBuscarRaza.getText().toString();
        tipo = etBuscarTipo.getText().toString();
        edad = (etBuscarEdad.getText().toString().trim().isEmpty()) ? 0 : Integer.parseInt(etBuscarEdad.getText().toString());
        peso = (etBuscarPeso.getText().toString().trim().isEmpty()) ? 0 : Integer.parseInt(etBuscarPeso.getText().toString());
        altura = (etBuscarAltura.getText().toString().trim().isEmpty()) ? 0 : Integer.parseInt(etBuscarAltura.getText().toString());


        if (nombremascota.isEmpty() || raza.isEmpty() || tipo.isEmpty() || color.isEmpty() || peso == 0 || altura == 0 || edad == 0) {
            notificar("Complete el formulario");
            etNombreMascotaB.requestFocus();
        } else {
            preguntarActualizar();
        }

    }

    private void buscarMascota(){
        SQLiteDatabase db = conexion.getWritableDatabase();
        String[] campoCriterio = {etIDMascotaBuscado.getText().toString()};
        String[] campos = {"nombremascota", "color", "edad", "raza", "tipo", "peso", "altura"};

        //EXCEPCIONES
        try{
            Cursor cursor = db.query("mascotas", campos, "idmascota=?", campoCriterio, null, null, null);
            cursor.moveToFirst();

            etNombreMascotaB.setText(cursor.getString(0));
            etBuscarColor.setText(cursor.getString(1));
            etBuscarEdad.setText(cursor.getString(2));
            etBuscarRaza.setText(cursor.getString(3));
            etBuscarTipo.setText(cursor.getString(4));
            etBuscarPeso.setText(cursor.getString(5));
            etBuscarAltura.setText(cursor.getString(6));
            cursor.close();
        }catch(Exception e){
            Toast.makeText(this,"No encontrado", Toast.LENGTH_SHORT).show();
        }
    }

    private void loadUI() {
        etIDMascotaBuscado = findViewById(R.id.etIDMascotaBuscado);
        etNombreMascotaB = findViewById(R.id.etNombreMascotaB);
        etBuscarColor = findViewById(R.id.etBuscarColor);
        etBuscarEdad = findViewById(R.id.etBuscarEdad);
        etBuscarRaza = findViewById(R.id.etBuscarRaza);
        etBuscarTipo = findViewById(R.id.etBuscarTipo);
        etBuscarPeso = findViewById(R.id.etBuscarPeso);
        etBuscarAltura = findViewById(R.id.etBuscarAltura);

        btBuscarMascota = findViewById(R.id.btBuscarMascota);
        btReiniciarRM = findViewById(R.id.btReiniciarRM);
        btActualizarMascota = findViewById(R.id.btActualizarMascota);
        btAEliminarMascota = findViewById(R.id.btAEliminarMascota);

    }


}