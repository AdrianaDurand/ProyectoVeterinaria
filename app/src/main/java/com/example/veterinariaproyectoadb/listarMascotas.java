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

public class listarMascotas extends AppCompatActivity {

    ListView lvRegistrosMascotas;
    ArrayList<String> listaInformacionMascotas;
    ArrayList<Animal> listaMascotas;
    ConexionSQLiteHelper conexion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar_mascotas);

        loadUI();

        conexion = new ConexionSQLiteHelper(getApplicationContext(), "dbveterinaria", null, 1);
        consultarListaMascotas();

        ArrayAdapter adaptador = new ArrayAdapter(this, android.R.layout.simple_list_item_1, listaInformacionMascotas);

        lvRegistrosMascotas.setAdapter(adaptador);

        lvRegistrosMascotas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                String mensaje = "";
                mensaje += "Peso: " + listaMascotas.get(position).getPeso()+"\n";
                mensaje += "Altura: " + listaMascotas.get(position).getAltura()+"\n";
                Toast.makeText(listarMascotas.this, mensaje, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void consultarListaMascotas() {
        SQLiteDatabase db = conexion.getReadableDatabase();
        Animal animal = null;
        listaMascotas = new ArrayList<Animal>();
        Cursor cursor = db.rawQuery("SELECT * FROM mascotas", null);

        while (cursor.moveToNext()) {
            animal = new Animal();
            animal.setIdmascota(cursor.getInt(0));
            animal.setNombremascota(cursor.getString(1));
            animal.setColor(cursor.getString(2));
            animal.setEdad(cursor.getInt(3));
            animal.setRaza(cursor.getString(4));
            animal.setTipo(cursor.getString(5));
            animal.setPeso(cursor.getInt(6));
            animal.setAltura(cursor.getInt(7));

            listaMascotas.add(animal);
        }
        obtenerLista();
    }
    private void obtenerLista() {
        //Paso 1: Construimos nuestra lista con los datos a mostrar
        listaInformacionMascotas = new ArrayList<String>();

        //Paso 2: Recorremos la colección de personas
        for (int i = 0; i < listaMascotas.size(); i++) {

            //Paso 3: Enviamos la información de la primera lista a la segunda
            listaInformacionMascotas.add(listaMascotas.get(i).getNombremascota() + " - " + listaMascotas.get(i).getRaza());
        }
    }

    private void loadUI(){
        lvRegistrosMascotas = findViewById(R.id.lvRegistrosMascotas);
    }
}


