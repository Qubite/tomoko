package io.qubite.tomoko.gson;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import io.qubite.tomoko.TomokoException;
import io.qubite.tomoko.patch.CommandType;
import io.qubite.tomoko.patch.OperationDto;
import io.qubite.tomoko.patch.Patch;
import io.qubite.tomoko.patch.PatchParseException;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.util.List;
import java.util.stream.Collectors;

public class PatchParser {

    private static final Type TYPE = CustomParametrizedType.of(List.class, GsonOperationDto.class);

    private final Gson gson;

    PatchParser(Gson gson) {
        this.gson = gson;
    }

    public static PatchParser instance() {
        return new PatchParser(new Gson());
    }

    public static PatchParser instance(Gson gson) {
        return new PatchParser(gson);
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
        CommandType type = CommandType.of(operationDto.getOp());
        if (type.equals(CommandType.REMOVE)) {
            return OperationDto.remove(operationDto.getPath());
        } else {
            if (operationDto.getValue() == null) {
                throw new PatchParseException("For ADD and REPLACE operations value must be specified.");
            }
            return OperationDto.of(operationDto.getPath(), type, GsonTree.of(operationDto.getValue()));
        }
    }

}
