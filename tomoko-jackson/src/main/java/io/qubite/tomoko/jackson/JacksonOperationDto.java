package io.qubite.tomoko.jackson;

import com.fasterxml.jackson.databind.JsonNode;

class JacksonOperationDto {

    private String path;
    private String op;
    private JsonNode value;

    JacksonOperationDto() {
    }

    JacksonOperationDto(String path, String op, JsonNode value) {
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

    public JsonNode getValue() {
        return value;
    }

}
