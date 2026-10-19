package ro.magazin.common.protocol;

import com.fasterxml.jackson.databind.JsonNode;

/**
 * Cerere trimisa de aplicatia client. Pe socket fiecare cerere ocupa o singura linie JSON.
 */
public class Request {

    private TipCerere tip;
    private JsonNode payload;

    public Request() {
    }

    public Request(TipCerere tip) {
        this(tip, null);
    }

    public Request(TipCerere tip, Object payload) {
        this.tip = tip;
        this.payload = JsonUtil.toTree(payload);
    }

    public TipCerere getTip() {
        return tip;
    }

    public void setTip(TipCerere tip) {
        this.tip = tip;
    }

    public JsonNode getPayload() {
        return payload;
    }

    public void setPayload(JsonNode payload) {
        this.payload = payload;
    }

    public <T> T getPayloadAs(Class<T> tipPayload) {
        return JsonUtil.convert(payload, tipPayload);
    }
}
