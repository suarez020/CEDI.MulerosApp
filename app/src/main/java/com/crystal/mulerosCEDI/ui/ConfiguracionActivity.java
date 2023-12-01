package com.crystal.mulerosCEDI.ui;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.crystal.mulerosCEDI.R;
import com.crystal.mulerosCEDI.common.Constantes;
import com.crystal.mulerosCEDI.common.LogFile;
import com.crystal.mulerosCEDI.common.SPM;
import com.crystal.mulerosCEDI.common.Utilidades;
import com.crystal.mulerosCEDI.retrofit.ClienteRetrofit;
import com.crystal.mulerosCEDI.retrofit.ServiceRetrofit;
import com.crystal.mulerosCEDI.retrofit.request.RequestConfiguracion;
import com.crystal.mulerosCEDI.retrofit.response.configuracion.ResponseConfiguracion;
import com.google.android.material.snackbar.Snackbar;
import java.net.NetworkInterface;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
public class ConfiguracionActivity extends AppCompatActivity implements View.OnClickListener {
    Utilidades util = new Utilidades();

    //Declaración del cliente REST
    ServiceRetrofit serviceRetrofit;
    ClienteRetrofit appCliente;

    //Declaración de los objetos de la interfaz del activity
    EditText etServidorConf, etPuertoConf, etEquipoConf;
    Button btnGuardarConf;
    ProgressBar pbConfiguracion;
    Animation animacionArriba, animacionAbajo;
    //TextToSpeech speech;
    Context contexto;

    //Variables
    String servidor, puerto, equipo, mac;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuracion);
        Utilidades.ocultarBarraEstado(getWindow());

        this.setTitle(R.string.menu_configuracion);
        Objects.requireNonNull(getSupportActionBar()).setSubtitle(R.string.app_name);

        if(SPM.getString(Constantes.API_POSSERVICE_URL) == null){
            Toast.makeText(this, "Configure el dispositivo", Toast.LENGTH_SHORT).show();
        }

        //Asignar referencias
        findViews();
        //Agregar animaciones
        animaciones();
        //Eventos
        eventos();
        //Permisos
        permisos();
    }

    private void animaciones() {
        animacionArriba = AnimationUtils.loadAnimation(this, R.anim.desplazamiento_arriba);
        animacionAbajo = AnimationUtils.loadAnimation(this, R.anim.desplazamiento_abajo);
    }

    private void findViews() {
        contexto = ConfiguracionActivity.this;

        etServidorConf = findViewById(R.id.etServidorConf);
        etServidorConf.requestFocus();
        etPuertoConf = findViewById(R.id.etPuertoConf);
        etEquipoConf = findViewById(R.id.etEquipoConf);
        btnGuardarConf = findViewById(R.id.btnGuardarConf);
        pbConfiguracion = findViewById(R.id.pbConfiguracion);
        pbConfiguracion.setVisibility(View.GONE);

        if(util.valConstVaciasConf()){
            cargarDatos();
        }
    }

    private void eventos() {
        btnGuardarConf.setOnClickListener(this);

        //Eventos sobre el EditText Equipo
        etEquipoConf.setImeActionLabel("IR", KeyEvent.KEYCODE_ENTER);
        etEquipoConf.setOnKeyListener(new View.OnKeyListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    equipo = etEquipoConf.getText().toString().replaceAll("\\s","");
                    if (!equipo.isEmpty()) {
                        validarCampos();
                    }
                    return true;
                }
                return false;
            }
        });

        etEquipoConf.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                boolean handled = false;
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    equipo = etEquipoConf.getText().toString().replaceAll("\\s","");
                    if(!equipo.isEmpty()){
                        validarCampos();
                    }
                }
                return handled;
            }
        });
    }

    public void validarCampos(){
        cogerValoresConf();
        btnGuardarConf.setEnabled(Utilidades.validarCamposVacios(servidor, puerto, equipo));
        if(Utilidades.validarCamposVacios(servidor, puerto, equipo)){
            btnGuardarConf.callOnClick();
        }
    }

    private void cargarDatos(){
        etServidorConf.setText(SPM.getString(Constantes.SERVIDOR_API));
        etPuertoConf.setText(SPM.getString(Constantes.PUERTO_API));
        etEquipoConf.setText(SPM.getString(Constantes.EQUIPO_API));
    }

    private void cogerValoresConf() {
        servidor = etServidorConf.getText().toString();
        puerto = etPuertoConf.getText().toString();
        equipo = etEquipoConf.getText().toString();
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.btnGuardarConf){
            cogerValoresConf();
            if(servidor.isEmpty()){
                mensajeFab("Error", "Servidor requerido", v);
            }else if(puerto.isEmpty()){
                mensajeFab("Error", "Puerto requerido", v);
            }else if(equipo.isEmpty()){
                mensajeFab("Error", "Equipo requerido", v);
            }else{
                mac = getMacAddr();
                Log.e("LOGCAT", "Dirección MAC: "+mac);
                if(!mac.equals("")){
                    iniciarProgressBar();
                    SPM.setString(Constantes.API_POSSERVICE_URL, "http://"+servidor+":"+puerto+"/Muleros/");
                    appCliente = ClienteRetrofit.obtenerInstancia();
                    serviceRetrofit = appCliente.obtenerServicios();

                    SPM.setString(Constantes.MAC_EQUIPO, mac);

                    apiConfiguracion();
                }else{
                    Utilidades.mensajeDialog("Error", "No se pudo obtener la direccion MAC, contacte con el administrador", contexto);
                }
            }
        }
    }

    private void apiConfiguracion(){
        //Consultar la Api de configuración
        RequestConfiguracion requestConfiguracion = new RequestConfiguracion(mac, equipo);
        Call<ResponseConfiguracion> call = serviceRetrofit.doConfiguracion(requestConfiguracion);
        call.enqueue(new Callback<ResponseConfiguracion>() {
            @Override
            public void onResponse(Call<ResponseConfiguracion> call, Response<ResponseConfiguracion> response) {
                if(response.isSuccessful()){
                    assert response.body() != null;
                    LogFile.adjuntarLog(response.body().getRespuesta().toString());

                    if(response.body().getRespuesta().getError().getStatus()){
                        Utilidades.mensajeDialog("Error", response.body().getRespuesta().getMensaje(), contexto);
                        pararProgressBar();
                    }else{
                        Log.i("LOGCAT", "RespuestaServicioConfiguracion: "+ response.body());
                        if(response.body().getRespuesta().getConfiguracion().getCambioEstacion()){
                            msjToast("Configuración realizada satisfactoriamente");
                            SPM.setString(Constantes.SERVIDOR_API, servidor);
                            SPM.setString(Constantes.PUERTO_API, puerto);
                            SPM.setString(Constantes.EQUIPO_API, equipo);
                            irLogin();
                        }else{
                            Utilidades.mensajeDialog("Error", response.body().getRespuesta().getMensaje(), contexto);
                            pararProgressBar();
                        }
                    }
                }else{
                    Utilidades.mensajeDialog("Error", "Error de conexión con el servicio web base.", contexto);
                    pararProgressBar();
                }
            }

            @Override
            public void onFailure(Call<ResponseConfiguracion> call, Throwable t) {
                LogFile.adjuntarLog("ErrorResponseConfiguracion", t);
                Utilidades.mensajeDialog("Error", "Error de conexión: " + t.getMessage(), contexto);
                pararProgressBar();
            }
        });
    }

    private void iniciarProgressBar() {
        pbConfiguracion.setVisibility(View.VISIBLE);
    }

    private void pararProgressBar() {
        pbConfiguracion.setVisibility(View.GONE);
    }

    public static String getMacAddr() {
        try {
            List<NetworkInterface> all = Collections.list(NetworkInterface.getNetworkInterfaces());
            for (NetworkInterface nif : all) {
                if (!nif.getName().equalsIgnoreCase("wlan0")) continue;

                byte[] macBytes = nif.getHardwareAddress();
                if (macBytes == null) {
                    return "";
                }

                StringBuilder res1 = new StringBuilder();
                for (byte b : macBytes) {
                    res1.append(String.format("%02X:",b));
                }

                if (res1.length() > 0) {
                    res1.deleteCharAt(res1.length() - 1);
                }
                return res1.toString();
            }
        } catch (Exception ex) {
            Log.e("LOGCAT", ex.getMessage());
        }
        return "02:00:00:00:00:00";
    }

    public void msjToast(String msj){
        Toast.makeText(this, msj, Toast.LENGTH_SHORT).show();
    }

    //Solicitar permisos necesario para la aplicación
    private void permisos(){
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    Constantes.PERMISO_WRITE_EXTERNAL_STORAGE);
        }

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    Constantes.PERMISO_READ_EXTERNAL_STORAGE);
        }

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.INTERNET)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.INTERNET},
                    Constantes.PERMISO_INTERNET);
        }

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_NETWORK_STATE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_NETWORK_STATE},
                    Constantes.PERMISO_ACCESS_NETWORK_STATE);
        }

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_WIFI_STATE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_WIFI_STATE},
                    Constantes.PERMISO_ACCESS_WIFI_STATE);
        }
    }

    private void irLogin() {
        Intent i = new Intent(this, LoginActivity.class);
        startActivity(i);
        finish();
    }

    @SuppressLint("WrongConstant")
    private void mensajeFab(String mode, String msj, View v) {
        if(mode.equals(getResources().getString(R.string.error))){
            Snackbar.make(v, msj, Snackbar.LENGTH_LONG)
                    .setBackgroundTint(getResources().getColor(R.color.white))
                    .setTextColor(getResources().getColor(R.color.rojo))
                    .setAction("Action", null).show();
        }else if(mode.equals(getResources().getString(R.string.exito))){
            Snackbar.make(v, msj, Snackbar.LENGTH_LONG)
                    .setBackgroundTint(getResources().getColor(R.color.white))
                    .setTextColor(getResources().getColor(R.color.verde))
                    .setAction("Action", null).show();
        }
    }

    @Override
    public void onBackPressed(){
        //Salir de la pantalla configuración
        salirConfiguracion();
    }

    private void salirConfiguracion() {
        int icon = R.mipmap.ic_launcher;
        //Alerta para confirmar el cierre de configuración
        AlertDialog.Builder builder = new AlertDialog.Builder(ConfiguracionActivity.this);
        builder.setTitle(getResources().getString(R.string.cerrar_configuracion))
                .setCancelable(false)
                .setIcon(icon)
                .setMessage(getResources().getString(R.string.cerrar_configuracion_confirmar))
                .setPositiveButton(R.string.confirmar,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                finish();
                            }
                        })
                .setNegativeButton(R.string.cancelar,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });

        AlertDialog alert = builder.create();
        alert.show();
    }
}