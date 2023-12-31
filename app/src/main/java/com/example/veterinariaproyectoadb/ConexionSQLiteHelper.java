package com.example.veterinariaproyectoadb;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class ConexionSQLiteHelper extends SQLiteOpenHelper {


    final String MASCOTAS = "" +
            "CREATE TABLE 'mascotas'(" +
            "'idmascota'       INTEGER NOT NULL," +
            "'nombremascota'   TEXT NOT NULL," +
            "'color'           TEXT NOT NULL," +
            "'edad'           INTEGER NOT NULL," +
            "'raza'           TEXT NOT NULL," +
            "'tipo'           TEXT NOT NULL," +
            "'peso'           INTEGER NOT NULL," +
            "'altura'           INTEGER NOT NULL," +
            "PRIMARY KEY     ('idmascota' AUTOINCREMENT)"+
            ")";


    final String DUENOS = "" +
            "CREATE TABLE 'duenos'(" +
            "'iddueno'          INTEGER NOT NULL," +
            "'apellidos'        TEXT NOT NULL," +
            "'nombres'          TEXT NOT NULL," +
            "'fechanacimiento'  TEXT NOT NULL," +
            "'telefono'         INTEGER NOT NULL," +
            "'email'            TEXT NOT NULL," +
            "'direccion'        TEXT NOT NULL," +
            "PRIMARY KEY     ('iddueno' AUTOINCREMENT)"+
            ")";

    public ConexionSQLiteHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(MASCOTAS);
        sqLiteDatabase.execSQL(DUENOS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1){
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS mascotas");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS duenos");
        onCreate(sqLiteDatabase);

    }

}
