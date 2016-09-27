package io.qubite.tomoko.jackson;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.qubite.tomoko.PatcherException;
import io.qubite.tomoko.json.JsonTree;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by edhendil on 19.08.16.
 */
class NodeFactory {

    private final ObjectMapper objectMapper;

    NodeFactory(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public JsonTree toTree(String json) {
        try {
            JsonNode jsonNode = objectMapper.readTree(json);
            return JacksonTree.of(jsonNode, objectMapper);
        } catch (IOException e) {
            throw new PatcherException("Cannot parse provided json", e);
        }
    }

    public JsonTree toTree(InputStream stream) {
        try {
            JsonNode jsonNode = objectMapper.readTree(stream);
            return JacksonTree.of(jsonNode, objectMapper);
        } catch (IOException e) {
            throw new PatcherException("Cannot parse provided json", e);
        }
    }

    public JsonTree toTree(JsonNode node) {
        return JacksonTree.of(node, objectMapper);
    }

}
