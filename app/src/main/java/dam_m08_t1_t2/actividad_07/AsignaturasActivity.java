package dam_m08_t1_t2.actividad_07;


import android.app.Activity;
import android.os.Bundle;

public class AsignaturasActivity extends Activity {

    // aplicacion de base de datos.
    protected AsignaturasBaseDatosCrear miBaseDatos = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        miBaseDatos = new AsignaturasBaseDatosCrear(this.getApplicationContext());
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(miBaseDatos != null)
        {
            miBaseDatos.close();
        }
    }
}
