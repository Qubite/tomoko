package io.qubite.tomoko.jackson;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.qubite.tomoko.json.CommandType;
import io.qubite.tomoko.json.OperationDto;
import io.qubite.tomoko.json.Patch;
import io.qubite.tomoko.type.Types;
import io.qubite.tomoko.type.ValueType;

import java.io.InputStream;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by edhendil on 03.09.16.
 */
public class PatchFactory {

    private static final ValueType<List<JacksonOperationDto>> TYPE = Types.list(JacksonOperationDto.class);

    private final NodeFactory nodeFactory;

    PatchFactory(NodeFactory nodeFactory) {
        this.nodeFactory = nodeFactory;
    }

    public static PatchFactory instance() {
        return new PatchFactory(new NodeFactory(new ObjectMapper()));
    }

    public static PatchFactory instance(ObjectMapper mapper) {
        return new PatchFactory(new NodeFactory(mapper));
    }

    public Patch parse(String json) {
        return Patch.of(nodeFactory.toTree(json).getAs(TYPE).stream().map(this::toOperationDto).collect(Collectors.toList()));
    }

    public Patch parse(InputStream inputStream) {
        return Patch.of(nodeFactory.toTree(inputStream).getAs(TYPE).stream().map(this::toOperationDto).collect(Collectors.toList()));
    }

    public Patch parse(JsonNode node) {
        return Patch.of(nodeFactory.toTree(node).getAs(TYPE).stream().map(this::toOperationDto).collect(Collectors.toList()));
    }

    private OperationDto toOperationDto(JacksonOperationDto operationDto) {
        return OperationDto.of(operationDto.getPath(), CommandType.of(operationDto.getOp()), nodeFactory.toTree(operationDto.getValue()));
    }

}
