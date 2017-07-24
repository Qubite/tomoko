package io.qubite.tomoko.patch;

import java.io.InputStream;
import java.util.List;

public interface OperationParser {

    List<OperationDto> toOperationDto(String json);

    List<OperationDto> toOperationDto(InputStream input);

}
