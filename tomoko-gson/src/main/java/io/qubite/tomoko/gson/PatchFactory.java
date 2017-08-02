package io.qubite.tomoko.gson;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import io.qubite.tomoko.TomokoException;
import io.qubite.tomoko.patch.CommandType;
import io.qubite.tomoko.patch.OperationDto;
import io.qubite.tomoko.patch.Patch;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.util.List;
import java.util.stream.Collectors;

public class PatchFactory {

    private static final Type TYPE = CustomParametrizedType.of(List.class, GsonOperationDto.class);

    private final Gson gson;

    PatchFactory(Gson gson) {
        this.gson = gson;
    }

    public static PatchFactory instance() {
        return new PatchFactory(new Gson());
    }

    public static PatchFactory instance(Gson gson) {
        return new PatchFactory(gson);
    }

    public Patch parse(String json) {
        List<GsonOperationDto> dtos = gson.fromJson(json, TYPE);
        return Patch.of(dtos.stream().map(this::toOperationDto).collect(Collectors.toList()));
    }

    public Patch parse(InputStream inputStream) {
        Reader reader = null;
        try {
            reader = new InputStreamReader(inputStream, "UTF-8");
            List<GsonOperationDto> dtos = gson.fromJson(reader, TYPE);
            return Patch.of(dtos.stream().map(this::toOperationDto).collect(Collectors.toList()));
        } catch (UnsupportedEncodingException e) {
            throw new TomokoException("Cannot parse provided input stream.", e);
        }
    }

    public Patch parse(JsonElement node) {
        List<GsonOperationDto> dtos = gson.fromJson(node, TYPE);
        return Patch.of(dtos.stream().map(this::toOperationDto).collect(Collectors.toList()));
    }

    private OperationDto toOperationDto(GsonOperationDto operationDto) {
        return OperationDto.of(operationDto.getPath(), CommandType.of(operationDto.getOp()), GsonTree.of(operationDto.getValue()));
    }

}
