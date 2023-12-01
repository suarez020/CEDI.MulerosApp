package com.crystal.mulerosCEDI.ui;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.crystal.mulerosCEDI.R;
import com.crystal.mulerosCEDI.common.Constantes;
import com.crystal.mulerosCEDI.common.SPM;
import com.crystal.mulerosCEDI.common.Utilidades;
import com.crystal.mulerosCEDI.retrofit.ClienteRetrofit;
import com.crystal.mulerosCEDI.retrofit.ServiceRetrofit;
import com.crystal.mulerosCEDI.retrofit.request.RequestCaja;
import com.crystal.mulerosCEDI.retrofit.request.RequestFinalizarConteo;
import com.crystal.mulerosCEDI.retrofit.request.RequestLecturaSKU;
import com.crystal.mulerosCEDI.retrofit.response.caja.ResponseCaja;
import com.crystal.mulerosCEDI.retrofit.response.finalizar_conteo.ResponseFinalizarConteo;
import com.crystal.mulerosCEDI.retrofit.response.lectura_sku.ResponseLecturaSKU;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
public class ConteoActivity extends AppCompatActivity implements View.OnClickListener {
    EditText P2etCaja,P2etSku,P2etCantidad,P2etCantidadLeida,P2etSkuParteFinal,P2etSource;
    Button btnFinalizarP2,btnAtrasP2;
    ConstraintLayout layoutFondo;
    //                                                                    Declaración del cliente REST
    ServiceRetrofit serviceRetrofit;
    ClienteRetrofit appCliente;
    //                                                                    Variables
    String cedulaConteo,estacionConteo,cajaConteo,skuConteo,proceso;
    boolean yaIngreso=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conteo);
        Utilidades.ocultarBarraEstado(getWindow());
        this.setTitle(R.string.pantalla_conteo);
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
        //variables para consumir los servicios con RETROFIT
        proceso=Constantes.PROCESO_CONTEO;
        estacionConteo = SPM.getString(Constantes.EQUIPO_API);
        cedulaConteo =SPM.getString(Constantes.CEDULA_USUARIO);

        layoutFondo=findViewById(R.id.clfondoPrincipalConteo);

        P2etCaja=findViewById(R.id.P2etCaja);
        P2etSku=findViewById(R.id.P2etSku);
        P2etCantidad=findViewById(R.id.P2etCantidad);
        P2etCantidadLeida=findViewById(R.id.P2etCantidadLeida);
        P2etSkuParteFinal=findViewById(R.id.P2etSkuParteFinal);
        btnFinalizarP2=findViewById(R.id.btnFinalizarP2);
        btnAtrasP2=findViewById(R.id.btnAtrasP2);
        P2etSource=findViewById(R.id.P2etSource);
    }

    private void eventos() {
        btnFinalizarP2.setOnClickListener(this);
        btnAtrasP2.setOnClickListener(this);
        P2etCaja.requestFocus();
        P2etCaja.setImeActionLabel("ir",KeyEvent.KEYCODE_ENTER);//evento de disparador
        P2etCaja.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((event.getAction()==KeyEvent.ACTION_DOWN)||(keyCode == KeyEvent.KEYCODE_ENTER)){
                    cajaConteo=P2etCaja.getText().toString().replaceAll("\\s","");
                    SPM.setString(Constantes.CAJA,cajaConteo);// reemplazampos la caja de nuevo
                    if (!cajaConteo.isEmpty()){
                        P2etSkuParteFinal.setEnabled(true);
                        P2etCaja.setEnabled(false);
                        irApiCaja();
                    }
                    return true;
                }
                return false;
            }
        });

        P2etSkuParteFinal.setOnEditorActionListener(new TextView.OnEditorActionListener(){
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event){
                if(actionId == EditorInfo.IME_ACTION_DONE){
                    irApiLecturaSku();
                }
                return false;
            }
        });

        P2etSkuParteFinal.setOnKeyListener(new View.OnKeyListener(){
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event){

                if ((event.getAction() == KeyEvent.ACTION_DOWN) || (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    skuConteo = P2etSkuParteFinal.getText().toString().replaceAll("\\s", "");

                    //SPM.setString(Constantes.SKU,skuConteo);
                    if (!skuConteo.isEmpty()&&!yaIngreso) {
                        yaIngreso=true;
                        P2etCaja.setEnabled(true);
                        irApiLecturaSku();
                    }
                    return true;
                }
                return false;
            }
        });
    }

    public void irApiCaja() {
        layoutFondo.setBackgroundColor(getResources().getColor(R.color.colorSecondary));
        //todo:servicio #1_Consultar la Api de http://127.0.0.1:8080/Muleros/Caja
        RequestCaja requestCaja =new RequestCaja(cedulaConteo,estacionConteo,cajaConteo,proceso);
        Call<ResponseCaja> call = serviceRetrofit.doCaja(requestCaja);
        call.enqueue(new Callback<ResponseCaja>()
        {
            @Override
            public void onResponse(Call<ResponseCaja> call, Response<ResponseCaja> response) {
                if(response.isSuccessful()) {
                    assert response.body() != null;

                    if(response.body().getRespuesta().getError().getStatus()){

                        mensajeSimpleDialog("Error", response.body().getRespuesta().getMensaje());
                        P2etCaja.setText("");//limpia la caja si es una erronea
                        P2etCaja.setEnabled(true);//lo habilitamos ya que esta false
                        P2etCaja.requestFocus();// pero debe seguir aca

                        P2etSkuParteFinal.setEnabled(false);// la idea es que no baje el usuario
                    }else {

                        P2etCantidadLeida.setText("");
                        P2etSkuParteFinal.setText("");
                        P2etSkuParteFinal.requestFocus();//nos posicionamos para consul¿mir el otro servicio

                        P2etSku.setText(response.body().getRespuesta().getCaja().getSku());
                        P2etCantidad.setText(response.body().getRespuesta().getCaja().getCantidad());


                    }
                }else {//vamos por la rama final si tiene un 200 pero el error es del servicio
                    mensajeSimpleDialog("Error", "Error de conexión con el servicio web base.");
                    P2etCaja.setText("");
                }
            }
            @Override
            public void onFailure(Call<ResponseCaja> call, Throwable t) {
                mensajeSimpleDialog("Error", "Error de conexión: " + t.getMessage());
            }
        });
    }

    public void irApiLecturaSku() {

        //todo:servicio #2_Consultar la Api de http://127.0.0.1:8080/Mulero/LecturaSKU
        RequestLecturaSKU requestLecturaSKU=new RequestLecturaSKU(cedulaConteo,estacionConteo,skuConteo);
        Call<ResponseLecturaSKU> call=serviceRetrofit.doLecturaSKU(requestLecturaSKU);
        call.enqueue(new Callback<ResponseLecturaSKU>() {

            @Override
            public void onResponse(Call<ResponseLecturaSKU> call, Response<ResponseLecturaSKU> response) {
                if(response.isSuccessful()) {
                    assert response.body() != null;
                    if(response.body().getRespuesta().getError().getStatus()){//si es true me entra
                        mensajeSimpleDialog("Error", response.body().getRespuesta().getMensaje());
                        P2etSkuParteFinal.setText("");
                        yaIngreso=false;
                        P2etSource.setText(response.body().getRespuesta().getError().getSource());
                        //se sabe que es error pero debe seguir contando no se si suba el contador
                        P2etCaja.setEnabled(false);//lo dejamos apagado arriba
                        P2etSkuParteFinal.requestFocus();//pero seguimos iterando aca
                    }else {
                        P2etSkuParteFinal.setText("");

                        P2etCantidadLeida.setText(response.body().getRespuesta().getCaja());
                        P2etSource.setText(response.body().getRespuesta().getError().getSource());

                        yaIngreso=false;
                        //se sabe que es error pero debe seguir contando no se si suba el contador
                        P2etCaja.setEnabled(false);//lo dejamos apagado
                        P2etSkuParteFinal.requestFocus();//pero seguimos iterando aca
                    }

                }else {// vamos por la rama final si tiene un 200 pero el error es del servicio
                    mensajeSimpleDialog("Error", "Error de conexión con el servicio web base.");
                    P2etSkuParteFinal.setText("");
                }
            }
            @Override
            public void onFailure(Call<ResponseLecturaSKU> call, Throwable t) {
                mensajeSimpleDialog("Error", "Error de conexión: " + t.getMessage());
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
                //todo:servicio #3_servicio de FinalizarConteo
            case R.id.btnFinalizarP2:
                RequestFinalizarConteo requestFinalizarConteo =new RequestFinalizarConteo(cedulaConteo,estacionConteo);
                Call<ResponseFinalizarConteo> call=serviceRetrofit.doFinalizarConteo(requestFinalizarConteo);
                call.enqueue(new Callback<ResponseFinalizarConteo>() {
                    @Override
                    public void onResponse(Call<ResponseFinalizarConteo> call, Response<ResponseFinalizarConteo> response) {
                        if(response.isSuccessful()) {

                            assert response.body() != null;

                            if(response.body().getRespuesta().getError().getStatus()){
                                layoutFondo.setBackgroundColor(getResources().getColor(R.color.rojo));//error true
                                //necesitamos que limpie y que me pinte la pantalla si es error, ademas que se ubique arriba

                                mensajeSimpleDialog("Error", response.body().getRespuesta().getMensaje());
                                P2etCaja.setText("");
                                P2etCaja.setEnabled(true);//antes de subir la activo
                                P2etCaja.requestFocus();//sube a caja

                                //si estamos alla arriba bloqueo el sku
                                P2etSkuParteFinal.setEnabled(false);

                                P2etSku.setText("");
                                P2etCantidad.setText("");
                                P2etCantidadLeida.setText("");
                                P2etSkuParteFinal.setText("");

                                P2etSource.setText(response.body().getRespuesta().getError().getSource());
                            }else {
                                layoutFondo.setBackgroundColor(getResources().getColor(R.color.verde));//error false
                                //necesitamos:que pinte la pantalla verde y me limpie los campos, ademas que se ubique arriba
                                P2etCaja.setText("");
                                P2etCaja.setEnabled(true);//antes de subir la activo
                                P2etCaja.requestFocus();//sube a caja

                                //si estamos alla arriba bloqueo el sku
                                P2etSkuParteFinal.setEnabled(false);

                                P2etSku.setText("");
                                P2etCantidad.setText("");
                                P2etCantidadLeida.setText("");
                                P2etSkuParteFinal.setText("");

                                P2etSource.setText(response.body().getRespuesta().getError().getSource());
                            }

                        }else {
                          mensajeSimpleDialog("Error", "Error de conexión con el servicio web base.");
                        }
                    }
                    @Override
                    public void onFailure(Call<ResponseFinalizarConteo> call, Throwable t) {
                        mensajeSimpleDialog("Error", "Error de conexión: " + t.getMessage());
                    }
                });
                break;
            case R.id.btnAtrasP2:
                Intent i = new Intent(ConteoActivity.this, PrincipalActivity.class);
                startActivity(i);
                break;
        }
    }
    // metodos x
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
        if(!(ConteoActivity.this.isFinishing())){
            alerta.show();
        }
    }
}