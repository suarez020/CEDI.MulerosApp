package com.crystal.mulerosCEDI.retrofit.response.lectura_sku;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResponseLecturaSKU {
    @SerializedName("Respuesta")
    @Expose
    private RespuestaLecturaSKU respuesta;

    public ResponseLecturaSKU(RespuestaLecturaSKU resLecturaSKU) {this.respuesta = resLecturaSKU;}

    public void setRespuesta(RespuestaLecturaSKU resLecturaSKU) {this.respuesta = resLecturaSKU;}

    public RespuestaLecturaSKU getRespuesta() {return respuesta;}
    @Override
    public String toString() {
        return "ResponseLecturaSKU{" +
                "respuesta=" + respuesta +
                '}';
    }
}
