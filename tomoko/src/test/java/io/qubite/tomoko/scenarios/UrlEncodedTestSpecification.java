package io.qubite.tomoko.scenarios;

import io.qubite.tomoko.specification.annotation.RemoveHandler;
import io.qubite.tomoko.specification.annotation.UrlEncoded;

public class UrlEncodedTestSpecification {

    private String parameter;

    @RemoveHandler("/files/{fileId}")
    public void urlEncodedRemove(@UrlEncoded("fileId") String fileId) {
        parameter = fileId;
    }

    public String getParameter() {
        return parameter;
    }
}
