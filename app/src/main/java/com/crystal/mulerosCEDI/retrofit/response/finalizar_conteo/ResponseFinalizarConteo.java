package com.crystal.mulerosCEDI.retrofit.response.finalizar_conteo;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
public class ResponseFinalizarConteo {
    @SerializedName("Respuesta")
    @Expose
    private RespuestaFinalizarConteo respuesta;

    public ResponseFinalizarConteo(RespuestaFinalizarConteo resFinalizarConteo) {this.respuesta = resFinalizarConteo;}

    public void setRespuesta(RespuestaFinalizarConteo resFinalizarConteo) {this.respuesta = resFinalizarConteo;}

    @Override
    public String toString() {
        return "ResponseFinalizarConteo{" +
                "respuesta=" + respuesta +
                '}';
    }

    public RespuestaFinalizarConteo getRespuesta() {
        return respuesta;
    }
}
