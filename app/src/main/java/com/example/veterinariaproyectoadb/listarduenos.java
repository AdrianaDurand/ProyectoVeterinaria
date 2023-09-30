package com.example.veterinariaproyectoadb;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class listarduenos extends AppCompatActivity {

    ListView lvRegistrosDuenos;

    ArrayList<String> listaInformacion;
    ArrayList<Persona> listaPersonas;
    ConexionSQLiteHelper conexion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listarduenos);

        loadUI();

        conexion = new ConexionSQLiteHelper(getApplicationContext(), "dbveterinaria", null, 1);
        consultarListaPersonas();

        ArrayAdapter adaptador = new ArrayAdapter(this, android.R.layout.simple_list_item_1, listaInformacion);

        lvRegistrosDuenos.setAdapter(adaptador);
        lvRegistrosDuenos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                String mensaje = "";
                mensaje += "Teléfono: " + listaPersonas.get(position).getTelefono() + "\n";
                mensaje += "Dirección: " + listaPersonas.get(position).getDireccion() + "\n";
                Toast.makeText(listarduenos.this, mensaje, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void consultarListaPersonas() {
        SQLiteDatabase db = conexion.getReadableDatabase();
        Persona persona = null;
        listaPersonas = new ArrayList<Persona>();
        Cursor cursor = db.rawQuery("SELECT * FROM duenos", null);

        while (cursor.moveToNext()) {
            persona = new Persona();
            persona.setIddueno(cursor.getInt(0));
            persona.setApellidos(cursor.getString(1));
            persona.setNombres(cursor.getString(2));
            persona.setFechanacimiento(cursor.getString(3));
            persona.setTelefono(cursor.getInt(4));
            persona.setEmail(cursor.getString(5));
            persona.setDireccion(cursor.getString(6));

            listaPersonas.add(persona);
        }
        obtenerLista();
    }
        private void obtenerLista() {
            //Paso 1: Construimos nuestra lista con los datos a mostrar
            listaInformacion = new ArrayList<String>();

            //Paso 2: Recorremos la colección de personas
            for (int i = 0; i < listaPersonas.size(); i++) {

                //Paso 3: Enviamos la información de la primera lista a la segunda
                listaInformacion.add(listaPersonas.get(i).getApellidos() + " " + listaPersonas.get(i).getNombres());
            }
        }

        private void loadUI(){
            lvRegistrosDuenos = findViewById(R.id.lvRegistrosDuenos);
        }
    }
