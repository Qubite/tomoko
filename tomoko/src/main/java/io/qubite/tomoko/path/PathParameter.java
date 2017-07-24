package io.qubite.tomoko.path;

/**
 * Created by edhendil on 19.07.17.
 */
public interface PathParameter<T> {

    T extractValue(Path path);

}
