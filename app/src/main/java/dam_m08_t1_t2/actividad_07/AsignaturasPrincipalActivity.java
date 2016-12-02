package dam_m08_t1_t2.actividad_07;

import java.util.Locale;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;

import dam_m08_t1_t2.actividad_07.AsignaturaBaseDatos.Notas;
import dam_m08_t1_t2.actividad_07.AsignaturaBaseDatos.Asignaturas;

// Pantalla de entrada de Asignaturas
public class AsignaturasPrincipalActivity extends AsignaturasActivity {
    // Se llama cuando se crea por primera vez la actividad.
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.asignaturasinicio);


        completarDesdeLaBaseDatos();

        // Manejar botón Guardar Asignatura
        final Button guardarAsignatura = (Button) findViewById(R.id.BotonGuardar);
        guardarAsignatura.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                final EditText asignatura = (EditText) findViewById(R.id.EditarTextoAsignatura);
                final EditText nota = (EditText) findViewById(R.id.EditarTextoNota);

                // Guardar nuevos registros
                SQLiteDatabase db = miBaseDatos.getWritableDatabase();
                db.beginTransaction();
                try {

                    long filaId = 0;
                    String clasificacion = nota.getText().toString();

                    // SQL Query
                    SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
                    queryBuilder.setTables(Notas.TABLA_NOTAS);
                    queryBuilder.appendWhere(Notas.CLASIFICACION_NOTA + "='"
                            + clasificacion + "'");

                    // Arrancar la Query
                    Cursor c = queryBuilder.query(db, null, null, null, null,
                            null, null);

                    if (c.getCount() == 0) {
                        // Añadir nueva nota
                        ContentValues anadirNota = new ContentValues();
                        anadirNota.put(Notas.CLASIFICACION_NOTA, clasificacion);
                        filaId = db.insert(Notas.TABLA_NOTAS,
                                Notas.CLASIFICACION_NOTA, anadirNota);
                    } else {
                        c.moveToFirst();
                        filaId = c.getLong(c.getColumnIndex(Notas._ID));
                    }

                    c.close();

                    // Siempre inserte nuevos registros de asignaturas, incluso si son iguales.
                    ContentValues anadirAsignaturas = new ContentValues();
                    anadirAsignaturas.put(Asignaturas.NOMBRE_ASIGNATURA, asignatura.getText()
                            .toString());
                    anadirAsignaturas.put(Asignaturas.NOTA_ID, filaId);
                    db.insert(Asignaturas.TABLA_ASIGNATURAS, Asignaturas.NOMBRE_ASIGNATURA,
                            anadirAsignaturas);

                    db.setTransactionSuccessful();
                } finally {
                    db.endTransaction();
                }

                // restablecer formulario pantalla.
                asignatura.setText(null);
                nota.setText(null);
                db.close();

            }
        });

        // ir a la pantalla de listado
        final Button listar = (Button) findViewById(R.id.BotonListarAsignaturas);
        listar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                // Ir a otra actividad que muestra la lista de asignaturas.
                Intent intent = new Intent(AsignaturasPrincipalActivity.this, AsignaturasListaActivity.class);
                startActivity(intent);
            }
        });

    }


    void completarDesdeLaBaseDatos()
    {
        SQLiteDatabase db = miBaseDatos.getReadableDatabase();
        Cursor c = db.query(Notas.TABLA_NOTAS, new String[] {Notas.CLASIFICACION_NOTA}, null, null,
                null, null, Notas.ORDEN_PREDETERMINADO);

        int numeroAsignaturas = c.getCount();
        String opcionesTexto[] = new String[numeroAsignaturas];
        if((numeroAsignaturas > 0) && (c.moveToFirst()))
        {
            for(int i = 0; i < numeroAsignaturas; i++)
            {
                opcionesTexto[i] = c.getString(c.getColumnIndex(Notas.CLASIFICACION_NOTA));
                c.moveToNext();
            }

            ArrayAdapter<String> adaptar =
                    new ArrayAdapter<String>(
                            this,
                            android.R.layout.simple_dropdown_item_1line,
                            opcionesTexto);

            AutoCompleteTextView texto = (AutoCompleteTextView) findViewById(R.id.EditarTextoNota);
            texto.setAdapter(adaptar);
        }

        c.close();
        db.close();

    }

}