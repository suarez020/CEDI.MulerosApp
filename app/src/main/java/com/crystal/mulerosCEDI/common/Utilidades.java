package com.crystal.mulerosCEDI.common;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Handler;
import android.speech.tts.TextToSpeech;
import android.view.Window;
import android.view.WindowManager;
import androidx.appcompat.app.AlertDialog;
import com.crystal.mulerosCEDI.R;
import java.util.Locale;

public class Utilidades {
    private Context contexto;
    private TextToSpeech speech;

    public Utilidades(){
    }

    public Utilidades(Context context) {
        this.contexto = context;
        speech =  new TextToSpeech(context, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status != TextToSpeech.ERROR){
                    speech.setLanguage(new Locale("spa", "ESP"));
                }
            }
        });
    }

    public boolean valConstVaciasConf(){
        return SPM.getString(Constantes.SERVIDOR_API) != null && SPM.getString(Constantes.PUERTO_API) != null && SPM.getString(Constantes.EQUIPO_API) !=null;
    }

    public static boolean validarCamposVacios(String... dato) {
        int i = 0;
        while (i<dato.length && !dato[i].isEmpty()){
            i++;
        }
        return i==dato.length;
    }

    public static boolean validarLength(String dato, Integer min, Integer max){
        return dato.length() >= min && dato.length() <= max;
    }

    //Alert Dialog para mostrar mensajes de error, alertas o información
    public static void mensajeDialog(String titulo, String msj, Context context){

        int icon = R.drawable.vector_alerta;
        if (titulo.equals("Error")){
            icon = R.drawable.vector_error;
        } else if(titulo.equals("Éxito")){
            icon = R.drawable.vector_exito;
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(titulo)
                .setCancelable(false)
                .setMessage(msj)
                .setIcon(icon)
                .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
        AlertDialog alerta = builder.create();
        alerta.show();
    }

    public static void ocultarBarraEstado(Window window){
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    public void toSpeech(final String msj) {
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if(speech != null){
                    speech.speak(msj, TextToSpeech.QUEUE_FLUSH, null);
                }
            }
        }, 100);
    }

    /*
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode==event.KEYCODE_BACK){
            AlertDialog.Builder builder=new AlertDialog.Builder(ChequeoActivity.this);
            builder.setMessage("Desea regresar a Menu Principal?")
                    .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent i = new Intent(ChequeoActivity.this, PrincipalActivity.class);
                            startActivity(i);
                            finish();
                        }
                    })
                    .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
            builder.show();
        }
        return super.onKeyDown(keyCode, event);
    }*/

    /*
    public void onBackPressed(){
            regresarPrincipal();
        }
        private void regresarPrincipal() {
            Intent i = new Intent(this, PrincipalActivity.class);
            startActivity(i);
            finish();
        }
     */
}
