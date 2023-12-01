package com.crystal.mulerosCEDI.retrofit.response.configuracion;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResponseConfiguracion {
    @SerializedName("Respuesta")
    @Expose
    private RespuestaConfiguracion respuesta;

    public ResponseConfiguracion(RespuestaConfiguracion resConfiguracion) {
        this.respuesta = resConfiguracion;
    }

    public RespuestaConfiguracion getRespuesta() {
        return respuesta;
    }

    public void setRespuesta(RespuestaConfiguracion resConfiguracion) {
        this.respuesta = resConfiguracion;
    }

    @Override
    public String toString() {
        return "ResponseConfiguracion{" +
                "respuesta=" + respuesta +
                '}';
    }
}
