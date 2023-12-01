package com.crystal.mulerosCEDI.ui;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.crystal.mulerosCEDI.R;
import com.crystal.mulerosCEDI.common.Constantes;
import com.crystal.mulerosCEDI.common.LogFile;
import com.crystal.mulerosCEDI.common.SPM;
import com.crystal.mulerosCEDI.common.Utilidades;
import com.crystal.mulerosCEDI.retrofit.ClienteRetrofit;
import com.crystal.mulerosCEDI.retrofit.ServiceRetrofit;
import com.crystal.mulerosCEDI.retrofit.request.RequestLogin;
import com.crystal.mulerosCEDI.retrofit.response.logout.ResponseLogout;
import java.util.Objects;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
public class PrincipalActivity extends AppCompatActivity implements View.OnClickListener{
    //Declaración del cliente REST
    ServiceRetrofit serviceRetrofit;
    ClienteRetrofit appCliente;

    //Declaración de los objetos de la interfaz del activity
    Button btnConteoReserva,btnbtnChequeoEan, btnSalir;
    String cedula, estacion;
    //TextToSpeech speech;

    CardView cvPrincipalE;

    TextView tvEquipoPrincipal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);
        Utilidades.ocultarBarraEstado(getWindow());

        this.setTitle(R.string.menu_principal);
        Objects.requireNonNull(getSupportActionBar()).setSubtitle(SPM.getString(Constantes.NOMBRE_USUARIO));

        //Iniciar el cliente REST
        inicioRetrofit();
        //Asignar referencias
        findViews();
        //Eventos
        eventos();
    }

    private void inicioRetrofit() {
        appCliente = ClienteRetrofit.obtenerInstancia();
        serviceRetrofit = appCliente.obtenerServicios();
    }

    private void findViews() {
        tvEquipoPrincipal = findViewById(R.id.tvEquipoPrincipal);
        tvEquipoPrincipal.setText(SPM.getString(Constantes.EQUIPO_API));
        cvPrincipalE = findViewById(R.id.cvPrincipalE);
        btnbtnChequeoEan = findViewById(R.id.btnChequeoEan);
        btnConteoReserva = findViewById(R.id.btnConteoReserva);
        btnSalir = findViewById(R.id.btnSalir);
    }

    private void eventos() {
        btnbtnChequeoEan.setOnClickListener(this);
        btnConteoReserva.setOnClickListener(this);
        btnSalir.setOnClickListener(this);
    }
    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnChequeoEan:
                irChequeo(v);
                break;
            case R.id.btnConteoReserva:
                irConteo();
                break;
                // para que se le envia la vista a un boton
            case R.id.btnSalir:
                irSalir(v);
                break;
        }
    }
    private void irChequeo(View view) {
        Intent i = new Intent(this, ChequeoActivity.class);
        startActivity(i);
        finish();
    }

    private void irConteo() {
        Intent i = new Intent(this, ConteoActivity.class);
        startActivity(i);
        finish();
    }
// para que se le envia la vista
    private void irSalir(View v) {
        cerrarSesion();
    }

    @Override
    public void onBackPressed(){
        cerrarSesion();
    }

    //Alert Dialog para mostrar mensajes de error, alertas o información
    public void mensajeSimpleDialog(String titulo, String msj){

        int icon = R.drawable.vector_alerta;
        if (titulo.equals(getResources().getString(R.string.error))) {
            icon = R.drawable.vector_error;
        } else if(titulo.equals(getResources().getString(R.string.exito))){
            icon = R.drawable.vector_exito;
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
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
        if(!(PrincipalActivity.this.isFinishing())){
            alerta.show();
        }
    }

    private void irLogin() {
        Intent intent = new Intent(PrincipalActivity.this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }

    //:ClassAd
    private void cerrarSesion() {
        int icon = R.drawable.vector_cerrar_sesion;
        //Alerta para confirmar el cierre de sesion
        AlertDialog.Builder builder = new AlertDialog.Builder(PrincipalActivity.this);
        builder.setTitle(getResources().getString(R.string.cerrar_sesion))
                .setCancelable(false)
                .setIcon(icon)
                .setMessage(getResources().getString(R.string.cerrar_sesion_confirmar))
                .setPositiveButton(R.string.confirmar,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                apiCerrarSesion();
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
    //:ClassAd
    private void apiCerrarSesion() {
        cedula = SPM.getString(Constantes.CEDULA_USUARIO);
        estacion = SPM.getString(Constantes.EQUIPO_API);
        //Consumir servicio para cerrar sesion
        RequestLogin requestLogout = new RequestLogin(cedula, estacion);
        Call<ResponseLogout> call = serviceRetrofit.doLogout(requestLogout);
        call.enqueue(new Callback<ResponseLogout>() {
            @Override
            public void onResponse(Call<ResponseLogout> call, Response<ResponseLogout> response) {
                if(response.isSuccessful()) {
                    assert response.body() != null;

                    LogFile.adjuntarLog(response.body().getRespuesta().toString());
                    if(response.body().getRespuesta().getError().getStatus()){
                        mensajeSimpleDialog("Error", response.body().getRespuesta().getMensaje());
                    }else{  //Cerrar sesió
                        irLogin();
                    }
                }else{
                    mensajeSimpleDialog("Error", "Error de conexión con el servicio web base. "+ response.message());
                }
            }
            @Override
            public void onFailure(Call<ResponseLogout> call, Throwable t) {
                LogFile.adjuntarLog("ErrorResponseLogout",t);
                mensajeSimpleDialog("Error", "Error de conexión: " + t.getMessage());
            }
        });
    }
}