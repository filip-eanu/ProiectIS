package ro.magazin.common.protocol;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.JsonNode;

import java.util.List;

/**
 * Raspunsul serverului la o cerere. Pe socket fiecare raspuns ocupa o singura linie JSON.
 */
public class Response {

    private StatusRaspuns status;
    private String mesaj;
    private JsonNode payload;

    public Response() {
    }

    private Response(StatusRaspuns status, String mesaj, Object payload) {
        this.status = status;
        this.mesaj = mesaj;
        this.payload = JsonUtil.toTree(payload);
    }

    public static Response ok() {
        return new Response(StatusRaspuns.OK, null, null);
    }

    public static Response ok(Object payload) {
        return new Response(StatusRaspuns.OK, null, payload);
    }

    public static Response eroare(String mesaj) {
        return new Response(StatusRaspuns.EROARE, mesaj, null);
    }

    public static Response notificare(String mesaj) {
        return new Response(StatusRaspuns.NOTIFICARE, mesaj, null);
    }

    @JsonIgnore
    public boolean esteOk() {
        return status == StatusRaspuns.OK;
    }

    public <T> T getPayloadAs(Class<T> tip) {
        return JsonUtil.convert(payload, tip);
    }

    public <T> List<T> getPayloadAsList(Class<T> tipElement) {
        return JsonUtil.convertList(payload, tipElement);
    }

    public StatusRaspuns getStatus() {
        return status;
    }

    public void setStatus(StatusRaspuns status) {
        this.status = status;
    }

    public String getMesaj() {
        return mesaj;
    }

    public void setMesaj(String mesaj) {
        this.mesaj = mesaj;
    }

    public JsonNode getPayload() {
        return payload;
    }

    public void setPayload(JsonNode payload) {
        this.payload = payload;
    }
}
