package io.qubite.tomoko.direct.dsl;

import io.qubite.tomoko.json.OperationDto;
import io.qubite.tomoko.path.Path;
import io.qubite.tomoko.specification.dsl.*;

/**
 * Created by edhendil on 29.08.16.
 */
public class OperationsDSL {

    public static OperationDto create(NullaryRemoveDescriptor descriptor) {
        Path path = descriptor.createPath();
        return OperationDto.remove(path.toString());
    }

    public static <A> OperationDto create(UnaryRemoveDescriptor<A> descriptor, A firstParameter) {
        Path path = descriptor.createPath(firstParameter);
        return OperationDto.remove(path.toString());
    }

    public static <A, B> OperationDto create(BinaryRemoveDescriptor descriptor, A firstParameter, B secondParameter) {
        Path path = descriptor.createPath(firstParameter, secondParameter);
        return OperationDto.remove(path.toString());
    }

    public static <A, B, C> OperationDto create(TernaryRemoveDescriptor descriptor, A firstParameter, B secondParameter, C thirdParameter) {
        Path path = descriptor.createPath(firstParameter, secondParameter, thirdParameter);
        return OperationDto.remove(path.toString());
    }

    public static <V> AddOperationBuilder create(NullaryAddDescriptor<V> descriptor, V value) {
        AddOperationBuilder addBuilder = AddOperationBuilder.create();
        addBuilder.add(descriptor, value);
        return addBuilder;
    }

    public static <A, V> AddOperationBuilder create(UnaryAddDescriptor<A, V> descriptor, A firstParameter, V value) {
        AddOperationBuilder addBuilder = AddOperationBuilder.create();
        addBuilder.add(descriptor, firstParameter, value);
        return addBuilder;
    }

    public static <A, B, V> AddOperationBuilder create(BinaryAddDescriptor<A, B, V> descriptor, A firstParameter, B secondParameter, V value) {
        AddOperationBuilder addBuilder = AddOperationBuilder.create();
        addBuilder.add(descriptor, firstParameter, secondParameter, value);
        return addBuilder;
    }

    public static <A, B, C, V> AddOperationBuilder create(TernaryAddDescriptor<A, B, C, V> descriptor, A firstParameter, B secondParameter, C thirdParameter, V value) {
        AddOperationBuilder addBuilder = AddOperationBuilder.create();
        addBuilder.add(descriptor, firstParameter, secondParameter, thirdParameter, value);
        return addBuilder;
    }

}
