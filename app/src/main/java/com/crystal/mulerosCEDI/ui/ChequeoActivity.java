package com.crystal.mulerosCEDI.ui;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.crystal.mulerosCEDI.R;
import com.crystal.mulerosCEDI.common.Constantes;
import com.crystal.mulerosCEDI.common.SPM;
import com.crystal.mulerosCEDI.common.Utilidades;
import com.crystal.mulerosCEDI.retrofit.ClienteRetrofit;
import com.crystal.mulerosCEDI.retrofit.ServiceRetrofit;
import com.crystal.mulerosCEDI.retrofit.request.RequestCaja;
import com.crystal.mulerosCEDI.retrofit.request.RequestSKUChequeo;
import com.crystal.mulerosCEDI.retrofit.response.caja.ResponseCaja;
import com.crystal.mulerosCEDI.retrofit.response.sku_chequeo.ResponseSKUChequeo;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
public class ChequeoActivity extends AppCompatActivity implements View.OnClickListener{
    //                                                                    Declaración del cliente REST
    ServiceRetrofit serviceRetrofit;
    ClienteRetrofit appCliente;
    ConstraintLayout layoutFondo;

    //                                                                    interfaz gafica objetos
    EditText etCaja,etSku,etCantidad,etSkuParteInferior,etSource;
    Button btnAtras;
    //                                                                     Variables
    String cedulaChequeo,estacion,caja,sku,proceso;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chequeo);
        Utilidades.ocultarBarraEstado(getWindow());
        this.setTitle(R.string.pantalla_chequeo);
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
        proceso=Constantes.PROCESO_CHEQUEO;
        estacion = SPM.getString(Constantes.EQUIPO_API);
        cedulaChequeo =SPM.getString(Constantes.CEDULA_USUARIO);
        layoutFondo=findViewById(R.id.clfondoPrincipalChequeo);
        etCaja=findViewById(R.id.etCaja);
        etSku=findViewById(R.id.etSku);
        etCantidad=findViewById(R.id.etCantidad);
        etSkuParteInferior=findViewById(R.id.etSkuParteInferior);
        etSource=findViewById(R.id.etSource);
        btnAtras=findViewById(R.id.btnAtras);
    }

    private void eventos() {
        btnAtras.setOnClickListener(this);
        etCaja.requestFocus();
        etCaja.setImeActionLabel("IR", KeyEvent.KEYCODE_ENTER);//evento de disparador
        etCaja.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN) || (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    caja = etCaja.getText().toString().replaceAll("\\s", "");
                    //SPM.setString(Constantes.CAJA,caja); guardar la caja

                    if (!caja.isEmpty()) {
                        etSkuParteInferior.setEnabled(true);
                        etCaja.setEnabled(false);
                        irApiCaja();
                    }
                    return true;
                }
                return false;
            }
        });
        etSkuParteInferior.setImeActionLabel("IR", KeyEvent.KEYCODE_ENTER);//evento de disparador
        etSkuParteInferior.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN) || (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    sku = etSkuParteInferior.getText().toString().replaceAll("\\s", "");

                    if (!sku.isEmpty()) {
                        etCaja.setEnabled(true);
                        etSkuParteInferior.setEnabled(false);
                        irApiSkuChequeo();
                    }
                    return true;
                }
                return false;
            }
        });
    }

    private void irApiCaja(){
        //color que aplicacion Original:
        layoutFondo.setBackgroundColor(getResources().getColor(R.color.colorSecondary));//azul volvemos al color que es
        //todo:servicio #1 Consumir Servicio la Api: http://127.0.0.1:8080/Muleros/Caja
        RequestCaja requestCaja =new RequestCaja(cedulaChequeo,estacion,caja,proceso);
        Call<ResponseCaja> call = serviceRetrofit.doCaja(requestCaja);
        call.enqueue(new Callback<ResponseCaja>()
        {
            @Override
            public void onResponse(Call<ResponseCaja> call, Response<ResponseCaja> response) {
                if(response.isSuccessful()) {
                    assert response.body() != null;
                    if(response.body().getRespuesta().getError().getStatus()){

                        mensajeSimpleDialog("Error", response.body().getRespuesta().getMensaje());
                        etCaja.setText("");
                        etCaja.setEnabled(true);//lo habilitamos ya que esta false
                        etCaja.requestFocus();// pero debe seguir aca

                        etSource.setText(response.body().getRespuesta().getError().getSource());//vemos que pasa con la caja
                        etSkuParteInferior.setEnabled(false);//laidea es que se quede y no pueda bajar
                    }else {

                        etSku.setText(response.body().getRespuesta().getCaja().getSku());//le metemos el sku
                        etCantidad.setText(response.body().getRespuesta().getCaja().getCantidad());//cantidad

                        etSkuParteInferior.setText("");
                        etSource.setText("");
                        etSkuParteInferior.requestFocus(); //vamos abajo
                    }

                }else {
                    mensajeSimpleDialog("Error", "Error de conexión con el servicio web base.");
                    etCaja.setText("");
                }
            }
            @Override
            public void onFailure(Call<ResponseCaja> call, Throwable t) {
                mensajeSimpleDialog("Error", "Error de conexión: " + t.getMessage());
            }
        });
    }

    private void irApiSkuChequeo(){
        //todo:servicio #2 Consumir Servicio la Api: http://127.0.0.1:8080/Muleros/SKUChequeo
        RequestSKUChequeo requestSKUChequeo =new RequestSKUChequeo(cedulaChequeo,estacion,sku);
        Call<ResponseSKUChequeo> call = serviceRetrofit.doSKUChequeo(requestSKUChequeo);
        call.enqueue(new Callback<ResponseSKUChequeo>()
        {
            @Override
            public void onResponse(Call<ResponseSKUChequeo> call, Response<ResponseSKUChequeo> response) {
                if(response.isSuccessful()) {
                    assert response.body() != null;
                     int tipoError=response.body().getRespuesta().getError().getCode();
                    if(response.body().getRespuesta().getError().getStatus()==true&&tipoError==999101) {                //seria true si entra hay error

                        layoutFondo.setBackgroundColor(getResources().getColor(R.color.rojo));//rojo error true alerta

                        etCaja.setText("");//si tenemos un error que limpie, y me permita hacer focus para seguir consumiendo
                        etSku.setText("");
                        etCantidad.setText("");
                        //--------------------------------------------------------------------------
                        etSkuParteInferior.setText("");//tambien quieren dejar solo el source nada mas

                        etSource.setText(response.body().getRespuesta().getError().getSource());
                        etCaja.requestFocus();//sube a caja de nuevo.

                    }else if(response.body().getRespuesta().getError().getStatus()){
                        etSkuParteInferior.setText("");
                        etSkuParteInferior.setEnabled(true);

                        etCaja.setEnabled(false);

                        etSkuParteInferior.requestFocus();//se queda aca en sku chequeo por ser este tipo de error
                        etSource.setText(response.body().getRespuesta().getError().getSource());//muestra error

                    }else {
                        layoutFondo.setBackgroundColor(getResources().getColor(R.color.verde));//verde error false=="ok"

                        etSource.setText(response.body().getRespuesta().getError().getSource());
                        etCaja.setText("");    //para subir a caja y que este vacia.
                        etSku.setText("");    //los campos de arriba limpios pa subir
                        etCantidad.setText("");//los campos de arriba limpios para subir
                        //--------------------------------------------------------------------------
                        etSkuParteInferior.setText("");//tambien quieren dejar solo el source nada mas no deja ver la info pero bueno

                        etCaja.requestFocus();//sube a caja de nuevo.
                    }

                }else {//vamos por la rama final si tiene un 200 pero el error es del servicio
                    mensajeSimpleDialog("Error", "Error de conexión con el servicio web base.");
                    etCaja.setText("");
                }
            }
            @Override
            public void onFailure(Call<ResponseSKUChequeo> call, Throwable t) {
                mensajeSimpleDialog("Error", "Error de conexión: " + t.getMessage());
            }
        });
    }

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
        if(!(ChequeoActivity.this.isFinishing())){
            alerta.show();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.btnAtras:
                Intent i = new Intent(this, PrincipalActivity.class);
                startActivity(i);
                finish();
                break;
        }
    }
}