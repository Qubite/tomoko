package io.qubite.tomoko.gson;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import io.qubite.tomoko.PatcherException;
import io.qubite.tomoko.patch.ValueTree;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;

class NodeFactory {

    private final Gson gson;

    NodeFactory(Gson gson) {
        this.gson = gson;
    }

    public ValueTree toTree(String json) {
        return GsonTree.of(gson.fromJson(json, JsonElement.class), gson);
    }

    public ValueTree toTree(InputStream inputStream) {
        try {
            Reader reader = new InputStreamReader(inputStream, "UTF-8");
            return GsonTree.of(gson.fromJson(reader, JsonElement.class), gson);
        } catch (UnsupportedEncodingException e) {
            throw new PatcherException("Cannot parse provided input stream.", e);
        }
    }

    public ValueTree toTree(JsonElement element) {
        return GsonTree.of(element, gson);
    }

}
