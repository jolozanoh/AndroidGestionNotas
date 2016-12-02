package dam_m08_t1_t2.actividad_07;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import dam_m08_t1_t2.actividad_07.AsignaturaBaseDatos.Notas;
import dam_m08_t1_t2.actividad_07.AsignaturaBaseDatos.Asignaturas;


// Esta clase se encarga de la creación y control de versiones de la base de datos de aplicaciones

class AsignaturasBaseDatosCrear extends SQLiteOpenHelper {

    private static final String NOMBRE_BASEDATOS = "asignaturas.db";
    private static final int VERSION_BASEDATOS = 1;

    AsignaturasBaseDatosCrear(Context contexto) {
        super(contexto, NOMBRE_BASEDATOS, null, VERSION_BASEDATOS);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        // Crear la tabla de notas
        db.execSQL("CREATE TABLE " + Notas.TABLA_NOTAS + " ("
                + Notas._ID + " INTEGER PRIMARY KEY AUTOINCREMENT ,"
                + Notas.CLASIFICACION_NOTA + " TEXT" + ");");

        // Crear la tabla de asignaturas
        db.execSQL("CREATE TABLE " + Asignaturas.TABLA_ASIGNATURAS + " (" + Asignaturas._ID
                + " INTEGER PRIMARY KEY AUTOINCREMENT ," + Asignaturas.NOMBRE_ASIGNATURA
                + " TEXT," + Asignaturas.NOTA_ID + " INTEGER" //clave ForenKey
                + ");");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
    }
}
