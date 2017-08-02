package io.qubite.tomoko;

import io.qubite.tomoko.handler.value.converter.ValueParser;

import java.util.Set;

public class TomokoConfiguration {

    private final Set<ValueParser<?>> valueParsers;

    TomokoConfiguration(Set<ValueParser<?>> valueParsers) {
        this.valueParsers = valueParsers;
    }

    public Set<ValueParser<?>> getValueParsers() {
        return valueParsers;
    }


}
