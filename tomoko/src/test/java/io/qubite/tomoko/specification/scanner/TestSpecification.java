package io.qubite.tomoko.specification.scanner;

import io.qubite.tomoko.patch.CommandType;
import io.qubite.tomoko.specification.annotation.*;

@PatcherConfiguration("/books")
public class TestSpecification {

    private String lastBookId;
    private String lastTitle;

    @PatcherHandler(path = "/{bookId}/title", action = CommandType.ADD)
    public void updateTitle(@Parameter("bookId") String otherParameterName, String title) {
        lastBookId = otherParameterName;
        lastTitle = title;
    }

    @ReplaceHandler("/{bookId}/title")
    public void replaceTitle(@Parameter("bookId") String otherParameterName, String title) {
        lastBookId = otherParameterName;
        lastTitle = title;
    }

    @RemoveHandler("/{bookId}/title")
    public void removeTitle(@Parameter("bookId") String bookId) {
        lastBookId = bookId;
    }

    public String getLastBookId() {
        return lastBookId;
    }

    public String getLastTitle() {
        return lastTitle;
    }

}
