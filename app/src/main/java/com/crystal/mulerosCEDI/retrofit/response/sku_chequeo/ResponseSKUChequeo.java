package com.crystal.mulerosCEDI.retrofit.response.sku_chequeo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResponseSKUChequeo {
    @SerializedName("Respuesta")
    @Expose
    private RespuestaSKUChequeo respuesta;

    public ResponseSKUChequeo(RespuestaSKUChequeo resSKUChequeo) {
        this.respuesta = resSKUChequeo;
    }


    public RespuestaSKUChequeo getRespuesta() {return respuesta;}
    public void setRespuesta(RespuestaSKUChequeo resSKUChequeo) {this.respuesta = resSKUChequeo;}

    @Override
    public String toString() {
        return "ResponseSKUChequeo{" +
                "respuesta=" + respuesta +
                '}';
    }
}
