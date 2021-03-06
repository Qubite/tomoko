package io.qubite.tomoko.direct;

import io.qubite.tomoko.handler.value.converter.ValueTreeParser;
import io.qubite.tomoko.path.converter.ConverterException;
import io.qubite.tomoko.type.ValueType;

/**
 * Parser for DirectTree implementation of the ValueTree interface.
 */
public class DirectTreeParser implements ValueTreeParser<DirectTree> {

    @Override
    public <T> T getAs(DirectTree directTree, ValueType<T> valueType) {
        if (!directTree.isLeaf()) {
            throw new ConverterException("Could not parse the provided value as " + valueType);
        }
        if (directTree.getValue() == null) {
            return null;
        }
        if (!valueType.getBaseClass().isInstance(directTree.getValue())) {
            throw new ConverterException("Could not parse the provided value as " + valueType);
        }
        return (T) directTree.getValue();
    }

    @Override
    public Class<DirectTree> supportedType() {
        return DirectTree.class;
    }

}
