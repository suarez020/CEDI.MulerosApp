package com.crystal.mulerosCEDI.retrofit.response.caja;

import com.crystal.mulerosCEDI.retrofit.response.login.RespuestaLogin;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResponseCaja {

    @SerializedName("Respuesta")
    @Expose
    private RespuestaCaja respuesta;

    public ResponseCaja(RespuestaCaja resCaja) {
        this.respuesta = resCaja;
    }

    public RespuestaCaja getRespuesta() {
        return respuesta;
    }

    public void setRespuesta(RespuestaCaja resCaja) {
        this.respuesta = resCaja;
    }

    @Override
    public String toString() {
        return "ResponseCaja{" +
                "respuesta=" + respuesta +
                '}';
    }
}
