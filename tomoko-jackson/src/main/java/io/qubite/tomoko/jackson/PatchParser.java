package io.qubite.tomoko.jackson;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import io.qubite.tomoko.patch.CommandType;
import io.qubite.tomoko.patch.OperationDto;
import io.qubite.tomoko.patch.Patch;
import io.qubite.tomoko.patch.PatchParseException;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.stream.Collectors;

public class PatchParser {

    private static final JavaType TYPE = TypeFactory.defaultInstance().constructCollectionType(List.class, JacksonOperationDto.class);

    private final ObjectMapper mapper;

    PatchParser(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    public static PatchParser instance() {
        return new PatchParser(new ObjectMapper());
    }

    public static PatchParser instance(ObjectMapper mapper) {
        return new PatchParser(mapper);
    }

    public Patch parse(String json) {
        try {
            List<JacksonOperationDto> dtos = mapper.readValue(json, TYPE);
            return toPatch(dtos);
        } catch (JsonMappingException e) {
            throw new PatchParseException("Value valueType mismatch between registered handler and received operation. Expected: " + JacksonOperationDto.class.getSimpleName(), e);
        } catch (IOException e) {
            throw new PatchParseException(e);
        }
    }

    public Patch parse(InputStream inputStream) {
        try {
            List<JacksonOperationDto> dtos = mapper.readValue(inputStream, TYPE);
            return toPatch(dtos);
        } catch (JsonMappingException e) {
            throw new PatchParseException("Value valueType mismatch between registered handler and received operation. Expected: " + JacksonOperationDto.class.getSimpleName(), e);
        } catch (IOException e) {
            throw new PatchParseException(e);
        }
    }

    public Patch parse(JsonNode node) {
        try {
            List<JacksonOperationDto> dtos = mapper.readValue(node.traverse(), TYPE);
            return toPatch(dtos);
        } catch (JsonMappingException e) {
            throw new PatchParseException("Value valueType mismatch between registered handler and received operation. Expected: " + JacksonOperationDto.class.getSimpleName(), e);
        } catch (IOException e) {
            throw new PatchParseException(e);
        }
    }

    private Patch toPatch(List<JacksonOperationDto> dtos) {
        return Patch.of(dtos.stream().map(this::toOperationDto).collect(Collectors.toList()));
    }

    private OperationDto toOperationDto(JacksonOperationDto operationDto) {
        CommandType type = CommandType.of(operationDto.getOp());
        if (type.equals(CommandType.REMOVE)) {
            return OperationDto.remove(operationDto.getPath());
        } else {
            if (operationDto.getValue() == null) {
                throw new PatchParseException("For ADD and REPLACE operations value must be specified.");
            }
            return OperationDto.of(operationDto.getPath(), type, JacksonTree.of(operationDto.getValue()));
        }
    }

}
