package io.qubite.tomoko.jackson;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.qubite.tomoko.PatcherException;
import io.qubite.tomoko.patch.ValueTree;

import java.io.IOException;
import java.io.InputStream;

class NodeFactory {

    private final ObjectMapper objectMapper;

    NodeFactory(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public ValueTree toTree(String json) {
        try {
            JsonNode jsonNode = objectMapper.readTree(json);
            return JacksonTree.of(jsonNode, objectMapper);
        } catch (IOException e) {
            throw new PatcherException("Cannot parse provided json", e);
        }
    }

    public ValueTree toTree(InputStream stream) {
        try {
            JsonNode jsonNode = objectMapper.readTree(stream);
            return JacksonTree.of(jsonNode, objectMapper);
        } catch (IOException e) {
            throw new PatcherException("Cannot parse provided json", e);
        }
    }

    public ValueTree toTree(JsonNode node) {
        return JacksonTree.of(node, objectMapper);
    }

}
