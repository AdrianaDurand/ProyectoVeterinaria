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

public class registrarduenos extends AppCompatActivity {
    EditText etApellidos, etNombres, etFechanac, etTelefono, etEmail, etDireccion;
    Button btRegistrarDueños, btAbrirBusqueda, btAbrirLista;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrarduenos);

        loadUI();

        btRegistrarDueños.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validarCampos();
            }
        });

        /*btAbrirBusqueda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), buscarClientes.class));
            }
        });*/
    }
    private void validarCampos(){
        String apellidos, nombres, fechanacimiento, telefono, email, direccion;

        apellidos           = etApellidos.getText().toString();
        nombres             = etNombres.getText().toString();
        fechanacimiento     = etFechanac.getText().toString();
        telefono            = etTelefono.getText().toString();
        email               = etEmail.getText().toString();
        direccion           = etDireccion.getText().toString();

        if(apellidos.isEmpty() || nombres.isEmpty() || apellidos.isEmpty() || fechanacimiento.isEmpty() || telefono.isEmpty() || email.isEmpty() || direccion.isEmpty()){
            notificar("Complete el formulario");
            etApellidos.requestFocus();
        }else{
            preguntar();
        }

    }

    private void preguntar() {
        AlertDialog.Builder dialogo = new AlertDialog.Builder(this);
        dialogo.setTitle("VETERINARIA");
        dialogo.setMessage("¿Está seguro de registrar a este dueño?");
        dialogo.setCancelable(false);

        dialogo.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                registrarDueno();}
        });

        dialogo.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        dialogo.show();
    }

    private void registrarDueno(){

        ConexionSQLiteHelper conexion = new ConexionSQLiteHelper(this,"dbveterinaria", null, 1);
        SQLiteDatabase db = conexion.getReadableDatabase();


        ContentValues parametros = new ContentValues();
        parametros.put("apellidos", etApellidos.getText().toString());
        parametros.put("nombres", etNombres.getText().toString());
        parametros.put("fechanacimiento", etFechanac.getText().toString());
        parametros.put("telefono", etTelefono.getText().toString());
        parametros.put("email", etEmail.getText().toString());
        parametros.put("direccion", etDireccion.getText().toString());


        long idobtenido = db.insert("duenos", "iddueno", parametros);
        notificar("Registro guardado - " + String.valueOf(idobtenido));
        reiniciar();
        etApellidos.requestFocus();
    }

    private void reiniciar(){
        etApellidos.setText(null);
        etNombres.setText(null);
        etFechanac.setText(null);
        etTelefono.setText(null);
        etEmail.setText(null);
        etDireccion.setText(null);
    }

    private void notificar(String mensaje){
        Toast.makeText(this, mensaje, Toast.LENGTH_LONG).show();
    }

    private void loadUI(){
        etApellidos = findViewById(R.id.etApellidos);
        etNombres = findViewById(R.id.etNombres);
        etFechanac = findViewById(R.id.etFechanac);
        etTelefono = findViewById(R.id.etTelefono);
        etEmail = findViewById(R.id.etEmail);
        etDireccion = findViewById(R.id.etDireccion);

        btRegistrarDueños = findViewById(R.id.btRegistrarDueños);
        btAbrirBusqueda = findViewById(R.id.btAbrirBusqueda);



    }
}


