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

public class buscarduenos extends AppCompatActivity {

    ConexionSQLiteHelper conexion;

    EditText etIDBuscado, etBuscarApellidos, etBuscarNombres, etBuscarFechanac, etBuscarTelefono, etBuscarEmail, etBuscarDireccion;
    Button btBuscarDueño, btReiniciar, btActualizarDueño, btAEliminarDueño;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buscarduenos);

        //Conexion BD
        conexion = new ConexionSQLiteHelper(getApplicationContext(), "dbveterinaria", null, 1);

        loadUI();

        //Eventos
        btBuscarDueño.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {buscarDueño();}
        });
        btActualizarDueño.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {validarCampos();}
        });
        btReiniciar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {reiniciarUI();}
        });

        btAEliminarDueño.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {preguntarEliminar();}
        });

    }

    private void notificar(String mensaje) {
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
                eliminarDueño();
            }
        });
        dialogo.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        dialogo.show();
    }

    private void eliminarDueño() {
        SQLiteDatabase db = conexion.getWritableDatabase();
        String[] campoCriterio = {etIDBuscado.getText().toString()};

        db.delete("duenos", "iddueno=?", campoCriterio);
        db.close();
        reiniciarUI();
        notificar("Registro eliminado correctamente");
        etIDBuscado.requestFocus();
    }

    private void preguntarActualizar(){
        AlertDialog.Builder dialogo = new AlertDialog.Builder(this);
        dialogo.setTitle("VETERINARIA");
        dialogo.setMessage("¿Estás seguro de actualizar?");
        dialogo.setCancelable(false);

        dialogo.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                actualizarDueño();}
        });
        dialogo.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {}
        });
    }

    private void actualizarDueño(){
        SQLiteDatabase db = conexion.getWritableDatabase();
        String[] campoCriterio = {etIDBuscado.getText().toString()};
        ContentValues parametros = new ContentValues();

        //PARAMETROS
        parametros.put("apellidos", etBuscarApellidos.getText().toString());
        parametros.put("nombres", etBuscarNombres.getText().toString());
        parametros.put("fechanacimiento", etBuscarFechanac.getText().toString());
        parametros.put("telefono", etBuscarTelefono.getText().toString());
        parametros.put("email", etBuscarEmail.getText().toString());
        parametros.put("direccion", etBuscarDireccion.getText().toString());

        db.update("duenos", parametros, "iddueno=?", campoCriterio);
        db.close();

        //REINICIAR
        notificar("Actualizado correctamente");
        reiniciarUI();
        etIDBuscado.requestFocus();
    }

    private void reiniciarUI(){
        etBuscarApellidos.setText(null);
        etBuscarNombres.setText(null);
        etBuscarFechanac.setText(null);
        etBuscarTelefono.setText(null);
        etBuscarEmail.setText(null);
        etBuscarDireccion.setText(null);
    }

    private void validarCampos() {
        String apellidos, nombres, fechanacimiento, telefono, email, direccion;

        apellidos = etBuscarApellidos.getText().toString();
        nombres = etBuscarNombres.getText().toString();
        fechanacimiento = etBuscarFechanac.getText().toString();
        telefono = etBuscarTelefono.getText().toString();
        email = etBuscarEmail.getText().toString();
        direccion = etBuscarDireccion.getText().toString();

        if (apellidos.isEmpty() || nombres.isEmpty() || fechanacimiento.isEmpty() || telefono.isEmpty() || email.isEmpty() || direccion.isEmpty()) {
            notificar("Complete el formulario");
            etBuscarApellidos.requestFocus();
        } else {
            preguntarActualizar();
        }
    }


    private void buscarDueño(){
        SQLiteDatabase db = conexion.getWritableDatabase();
        String[] campoCriterio = {etIDBuscado.getText().toString()};
        String[] campos = {"apellidos", "nombres", "fechanacimiento", "telefono", "email", "direccion"};

        //EXCEPCIONES
        try{
            Cursor cursor = db.query("duenos", campos, "iddueno=?", campoCriterio, null, null, null);
            cursor.moveToFirst();

            etBuscarApellidos.setText(cursor.getString(0));
            etBuscarNombres.setText(cursor.getString(1));
            etBuscarFechanac.setText(cursor.getString(2));
            etBuscarTelefono.setText(cursor.getString(3));
            etBuscarEmail.setText(cursor.getString(4));
            etBuscarDireccion.setText(cursor.getString(5));

            cursor.close();
        }catch(Exception e){
            Toast.makeText(this,"No encontrado", Toast.LENGTH_SHORT).show();
        }
    }
    private void loadUI(){
            etIDBuscado = findViewById(R.id.etIDBuscado);
            etBuscarApellidos = findViewById(R.id.etBuscarApellidos);
            etBuscarNombres = findViewById(R.id.etBuscarNombres);
            etBuscarFechanac = findViewById(R.id.etBuscarFechanac);
            etBuscarTelefono = findViewById(R.id.etBuscarTelefono);
            etBuscarEmail = findViewById(R.id.etBuscarEmail);
            etBuscarDireccion = findViewById(R.id.etBuscarDireccion);

            btBuscarDueño = findViewById(R.id.btBuscarDueño);
            btReiniciar = findViewById(R.id.btReiniciar);
            btActualizarDueño = findViewById(R.id.btActualizarDueño);
            btAEliminarDueño = findViewById(R.id.btAEliminarDueño);

    }

}