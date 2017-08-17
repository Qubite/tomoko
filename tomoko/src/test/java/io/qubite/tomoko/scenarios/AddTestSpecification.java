package io.qubite.tomoko.scenarios;

import io.qubite.tomoko.specification.annotation.AddHandler;
import io.qubite.tomoko.specification.annotation.PatcherConfiguration;

import java.io.IOException;

@PatcherConfiguration("/books")
public class AddTestSpecification {

    @AddHandler("/{bookId}/title")
    public void updateTitle(String title) {

    }

    @AddHandler("/{bookId}/author/firstName")
    public void updateAuthorFirstName(String firstName) {

    }

    @AddHandler("/{bookId}/author/lastName")
    public void updateAuthorLastName(String lastName) {

    }

    @AddHandler("/{bookId}/runtimeException")
    public void throwRuntimeException(String lastName) {
        throw new IllegalArgumentException("Runtime exception");
    }

    @AddHandler("/{bookId}/checkedException")
    public void throwCheckedException(String lastName) throws IOException {
        throw new IOException();
    }

}
