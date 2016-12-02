package dam_m08_t1_t2.actividad_07;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import dam_m08_t1_t2.actividad_07.AsignaturaBaseDatos.Notas;
import dam_m08_t1_t2.actividad_07.AsignaturaBaseDatos.Asignaturas;

// pantalla de asignaturas.
public class AsignaturasListaActivity extends AsignaturasActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.asignaturaslistado);

        // Llenar la tabla con los resultados de la base de datos
        listarAsignaturas();

        // Boton ir a la lista
        final Button anadirMasAsignaturas = (Button) findViewById(R.id.BotonMasAsignaturas);
        anadirMasAsignaturas.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Ir a otra pantalla que muestra la lista de asignaturas.
                finish();
            }
        });
    }



    public void listarAsignaturas()
    {
        // TableLayout donde queremos lista de visualización
        final TableLayout TablaAsignaturas = (TableLayout) findViewById(R.id.TablaListaAsignaturas);

        // SQL Query
        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
        queryBuilder.setTables(Asignaturas.TABLA_ASIGNATURAS +", " + Notas.TABLA_NOTAS);
        queryBuilder.appendWhere(Asignaturas.TABLA_ASIGNATURAS + "." + Asignaturas.NOTA_ID + "=" + Notas.TABLA_NOTAS + "." + Notas._ID);

        // Obtener la base de datos y ejecutar la consulta
        SQLiteDatabase db = miBaseDatos.getReadableDatabase();
        String obtenerDatos[] = { Asignaturas.TABLA_ASIGNATURAS + "." + Asignaturas.NOMBRE_ASIGNATURA, Asignaturas.TABLA_ASIGNATURAS + "." + Asignaturas._ID, Notas.TABLA_NOTAS + "." + Notas.CLASIFICACION_NOTA};
        Cursor c = queryBuilder.query(db, obtenerDatos, null, null, null, null, Asignaturas.ORDEN_PREDETERMINADO);

        // Muestra los resultados mediante la adición de algunas filas de tabla a la disposición de la tabla existente
        if(c.moveToFirst())
        {
            for(int i = 0; i< c.getCount(); i++)
            {
                TableRow nuevaFila = new TableRow(this);
                TextView nombreColumna = new TextView(this);
                TextView tipoColumna = new TextView(this);
                Button botonBorrar = new Button(this);

                nuevaFila.setTag(c.getInt(c.getColumnIndex(Asignaturas._ID)));
                nombreColumna.setText(c.getString(c.getColumnIndex(Asignaturas.NOMBRE_ASIGNATURA)));
                tipoColumna.setText(c.getString(c.getColumnIndex(Notas.CLASIFICACION_NOTA)));


                botonBorrar.setText("BORRAR");
                botonBorrar.setTag(c.getInt(c.getColumnIndex(Asignaturas._ID)));
                botonBorrar.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {

                        // Borrar asignatura
                        Integer id = (Integer) v.getTag();
                        borrarAsignatura(id);

                        // Encontrar y destruir la fila etiquetada con la identificación de la asignatura eliminado en la tabla
                        final TableLayout TablaAsignatura = (TableLayout) findViewById(R.id.TablaListaAsignaturas);
                        View borrarVista = TablaAsignatura.findViewWithTag(id);
                        TablaAsignatura.removeView(borrarVista);


                    }
                });

                nuevaFila.addView(nombreColumna);
                nuevaFila.addView(tipoColumna);
                nuevaFila.addView(botonBorrar);
                TablaAsignaturas.addView(nuevaFila);
                c.moveToNext();
            }
        }
        else
        {
            TableRow nuevaFila = new TableRow(this);
            TextView noResultados = new TextView(this);
            noResultados.setText("NO HAY RESULTADOS.");
            nuevaFila.addView(noResultados);
            TablaAsignaturas.addView(nuevaFila);
        }
        c.close();
        db.close();

    }

    public void borrarAsignatura(Integer id)
    {
        SQLiteDatabase db = miBaseDatos.getWritableDatabase();
        String borrado[] = { id.toString() };
        db.delete(Asignaturas.TABLA_ASIGNATURAS, Asignaturas._ID + "=?",borrado );
        db.close();
    }

}
