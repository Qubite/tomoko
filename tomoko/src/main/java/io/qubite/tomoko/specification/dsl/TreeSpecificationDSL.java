package io.qubite.tomoko.specification.dsl;

import io.qubite.tomoko.path.PathTemplate;
import io.qubite.tomoko.specification.TreeSpecification;
import io.qubite.tomoko.specification.TreeSpecificationBuilder;

/**
 * Created by edhendil on 29.08.16.
 */
public class TreeSpecificationDSL {

    private final TreeSpecificationBuilder builder;

    TreeSpecificationDSL(TreeSpecificationBuilder builder) {
        this.builder = builder;
    }

    public static TreeSpecificationDSL root() {
        return new TreeSpecificationDSL(TreeSpecification.builder());
    }

    public NullaryPath path() {
        return new NullaryPath(builder, PathTemplate.empty());
    }

    public TreeSpecification build() {
        return builder.build();
    }

}