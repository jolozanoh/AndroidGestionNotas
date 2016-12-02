package dam_m08_t1_t2.actividad_07;

import android.provider.BaseColumns;

// Utilizamos esta clase para realizar un seguimiento de la información de esquema de base como los nombres de tablas y columnas
public final class AsignaturaBaseDatos {

    private AsignaturaBaseDatos() {}

    // Tabla asignaturas
    public static final class Asignaturas implements BaseColumns {
        private Asignaturas() {}
        public static final String TABLA_ASIGNATURAS = "tabla_asignaturas";
        public static final String NOMBRE_ASIGNATURA = "nombre_asignatura";
        public static final String NOTA_ID = "nota_id";
        public static final String ORDEN_PREDETERMINADO = "nombre_asignatura ASC";
    }

    // Tabla notas
    public static final class Notas implements BaseColumns {
        private Notas() {}
        public static final String TABLA_NOTAS = "tabla_notas";
        public static final String CLASIFICACION_NOTA = "nota";
        public static final String ORDEN_PREDETERMINADO = "nota ASC";
    }
}