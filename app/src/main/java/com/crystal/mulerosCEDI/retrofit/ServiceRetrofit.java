package com.crystal.mulerosCEDI.retrofit;
import com.crystal.mulerosCEDI.common.Constantes;
import com.crystal.mulerosCEDI.retrofit.request.RequestCaja;
import com.crystal.mulerosCEDI.retrofit.request.RequestConfiguracion;
import com.crystal.mulerosCEDI.retrofit.request.RequestFinalizarConteo;
import com.crystal.mulerosCEDI.retrofit.request.RequestLecturaSKU;
import com.crystal.mulerosCEDI.retrofit.request.RequestLogin;
import com.crystal.mulerosCEDI.retrofit.request.RequestSKUChequeo;
import com.crystal.mulerosCEDI.retrofit.response.caja.ResponseCaja;
import com.crystal.mulerosCEDI.retrofit.response.configuracion.ResponseConfiguracion;
import com.crystal.mulerosCEDI.retrofit.response.finalizar_conteo.ResponseFinalizarConteo;
import com.crystal.mulerosCEDI.retrofit.response.inicio.ResponseInicio;
import com.crystal.mulerosCEDI.retrofit.response.lectura_sku.ResponseLecturaSKU;
import com.crystal.mulerosCEDI.retrofit.response.login.ResponseLogin;
import com.crystal.mulerosCEDI.retrofit.response.logout.ResponseLogout;
import com.crystal.mulerosCEDI.retrofit.response.sku_chequeo.ResponseSKUChequeo;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ServiceRetrofit {

    @Headers({"entorno: "+ Constantes.ENTORNO_API})
    @GET("inicio")
    Call<ResponseInicio> doInicio(@Query("MAC") String mac);

    @Headers({"entorno: "+ Constantes.ENTORNO_API})
    @POST("logout")
    Call<ResponseLogout> doLogout(@Body RequestLogin requestLogin);

    @Headers({"entorno: "+ Constantes.ENTORNO_API})
    @POST("login")
    Call<ResponseLogin> doLogin(@Body RequestLogin requestLogin);

    @Headers({"entorno: "+ Constantes.ENTORNO_API})
    @POST("Configuracion")
    Call<ResponseConfiguracion> doConfiguracion(@Body RequestConfiguracion requestConfiguracion);

    @Headers({"entorno: "+ Constantes.ENTORNO_API})
     @POST("Caja")
    Call<ResponseCaja> doCaja(@Body RequestCaja requestCaja);

    @Headers({"entorno: "+ Constantes.ENTORNO_API})
    @POST("SKUChequeo")
    Call<ResponseSKUChequeo> doSKUChequeo(@Body RequestSKUChequeo requestSKUChequeo);

    @Headers({"entorno: "+ Constantes.ENTORNO_API})
    @POST("LecturaSKU")
    Call<ResponseLecturaSKU> doLecturaSKU(@Body RequestLecturaSKU requestLecturaSKU);

    @Headers({"entorno: "+ Constantes.ENTORNO_API})
    @POST("FinalizarConteo")
    Call<ResponseFinalizarConteo> doFinalizarConteo(@Body RequestFinalizarConteo requestFinalizarConteo);
}
