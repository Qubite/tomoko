package io.qubite.tomoko.jackson;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import io.qubite.tomoko.TomokoException;
import io.qubite.tomoko.patch.CommandType;
import io.qubite.tomoko.patch.OperationDto;
import io.qubite.tomoko.patch.Patch;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.stream.Collectors;

public class PatchFactory {

    private static final JavaType TYPE = TypeFactory.defaultInstance().constructCollectionType(List.class, JacksonOperationDto.class);

    private final ObjectMapper mapper;

    PatchFactory(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    public static PatchFactory instance() {
        return new PatchFactory(new ObjectMapper());
    }

    public static PatchFactory instance(ObjectMapper mapper) {
        return new PatchFactory(mapper);
    }

    public Patch parse(String json) {
        try {
            List<JacksonOperationDto> dtos = mapper.readValue(json, TYPE);
            return toPatch(dtos);
        } catch (JsonMappingException e) {
            throw new TomokoException("Value valueType mismatch between registered handler and received operation. Expected: " + JacksonOperationDto.class.getSimpleName(), e);
        } catch (IOException e) {
            throw new TomokoException(e);
        }
    }

    public Patch parse(InputStream inputStream) {
        try {
            List<JacksonOperationDto> dtos = mapper.readValue(inputStream, TYPE);
            return toPatch(dtos);
        } catch (JsonMappingException e) {
            throw new TomokoException("Value valueType mismatch between registered handler and received operation. Expected: " + JacksonOperationDto.class.getSimpleName(), e);
        } catch (IOException e) {
            throw new TomokoException(e);
        }
    }

    public Patch parse(JsonNode node) {
        try {
            List<JacksonOperationDto> dtos = mapper.readValue(node.traverse(), TYPE);
            return toPatch(dtos);
        } catch (JsonMappingException e) {
            throw new TomokoException("Value valueType mismatch between registered handler and received operation. Expected: " + JacksonOperationDto.class.getSimpleName(), e);
        } catch (IOException e) {
            throw new TomokoException(e);
        }
    }

    private Patch toPatch(List<JacksonOperationDto> dtos) {
        return Patch.of(dtos.stream().map(this::toOperationDto).collect(Collectors.toList()));
    }

    private OperationDto toOperationDto(JacksonOperationDto operationDto) {
        return OperationDto.of(operationDto.getPath(), CommandType.of(operationDto.getOp()), JacksonTree.of(operationDto.getValue()));
    }

}
