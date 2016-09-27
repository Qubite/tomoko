package io.qubite.tomoko.gson;

import com.google.gson.JsonElement;

/**
 * Created by edhendil on 16.08.16.
 */
class GsonOperationDto {

    private String path;
    private String op;
    private JsonElement value;

    public GsonOperationDto() {
    }

    public GsonOperationDto(String path, String op, JsonElement value) {
        this.path = path;
        this.op = op;
        this.value = value;
    }

    public String getPath() {
        return path;
    }

    public String getOp() {
        return op;
    }

    public JsonElement getValue() {
        return value;
    }

}
