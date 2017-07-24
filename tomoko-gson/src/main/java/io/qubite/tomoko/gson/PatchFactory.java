package io.qubite.tomoko.gson;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import io.qubite.tomoko.patch.CommandType;
import io.qubite.tomoko.patch.OperationDto;
import io.qubite.tomoko.patch.Patch;
import io.qubite.tomoko.type.Types;
import io.qubite.tomoko.type.ValueType;

import java.io.InputStream;
import java.util.List;
import java.util.stream.Collectors;

public class PatchFactory {

    private static final ValueType<List<GsonOperationDto>> TYPE = Types.list(GsonOperationDto.class);

    private final NodeFactory nodeFactory;

    PatchFactory(NodeFactory nodeFactory) {
        this.nodeFactory = nodeFactory;
    }

    public static PatchFactory instance() {
        return new PatchFactory(new NodeFactory(new Gson()));
    }

    public static PatchFactory instance(Gson gson) {
        return new PatchFactory(new NodeFactory(gson));
    }

    public Patch parse(String json) {
        return Patch.of(nodeFactory.toTree(json).getAs(TYPE).stream().map(this::toOperationDto).collect(Collectors.toList()));
    }

    public Patch parse(InputStream inputStream) {
        return Patch.of(nodeFactory.toTree(inputStream).getAs(TYPE).stream().map(this::toOperationDto).collect(Collectors.toList()));
    }

    public Patch parse(JsonElement node) {
        return Patch.of(nodeFactory.toTree(node).getAs(TYPE).stream().map(this::toOperationDto).collect(Collectors.toList()));
    }

    private OperationDto toOperationDto(GsonOperationDto operationDto) {
        return OperationDto.of(operationDto.getPath(), CommandType.of(operationDto.getOp()), nodeFactory.toTree(operationDto.getValue()));
    }

}
