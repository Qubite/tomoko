package io.qubite.tomoko.direct;

import io.qubite.tomoko.PatcherException;
import io.qubite.tomoko.handler.value.converter.ValueParser;
import io.qubite.tomoko.type.ValueType;

public class DirectTreeParser implements ValueParser<DirectTree> {

    @Override
    public <T> T getAs(DirectTree directTree, ValueType<T> valueType) {
        if (!directTree.isLeaf()) {
            throw new PatcherException("No value present");
        }
        if (directTree.getValue() == null) {
            return null;
        }
        if (!valueType.getBaseClass().isInstance(directTree.getValue())) {
            throw new PatcherException("Value valueType mismatch between registered handler and received operation. Expected: " + valueType + ", got " + directTree.getValue().getClass().getSimpleName());
        }
        return (T) directTree.getValue();
    }

    @Override
    public Class<DirectTree> supportedType() {
        return DirectTree.class;
    }

}
