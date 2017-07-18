package io.qubite.tomoko.json;

import java.io.InputStream;
import java.util.List;

public interface JsonParser {

    List<OperationDto> toOperationDto(String json);

    List<OperationDto> toOperationDto(InputStream input);

}
