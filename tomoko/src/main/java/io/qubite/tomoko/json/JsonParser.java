package io.qubite.tomoko.json;

import java.io.InputStream;
import java.util.List;

/**
 * Created by edhendil on 16.08.16.
 */
public interface JsonParser {

    List<OperationDto> toOperationDto(String json);

    List<OperationDto> toOperationDto(InputStream input);

}
