package io.qubite.tomoko.specification.scanner;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.Assert.assertEquals;

public class PathPatternTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(PathPatternTest.class);

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void parse_empty() throws Exception {
        PathPattern parsed = PathPattern.parse("");
        assertEquals(0, parsed.parameterCount());
        assertEquals(0, parsed.size());
    }

    @Test
    public void parse_validStatic() throws Exception {
        PathPattern parsed = PathPattern.parse("/nodeName");
        assertEquals(0, parsed.parameterCount());
        assertEquals(1, parsed.size());
    }

    @Test
    public void parse_validWildcardParameter() throws Exception {
        PathPattern parsed = PathPattern.parse("/{parameter}");
        assertEquals(1, parsed.parameterCount());
        assertEquals(1, parsed.size());
    }

    @Test
    public void parse_validRegexParameter() throws Exception {
        PathPattern parsed = PathPattern.parse("/{parameter:[a-z]}");
        assertEquals(1, parsed.parameterCount());
        assertEquals(1, parsed.size());
    }

    @Test
    public void parse_validParameters() throws Exception {
        PathPattern parsed = PathPattern.parse("/{parameter}/{another:[a-z]}/{andYetAnother}/static");
        assertEquals(3, parsed.parameterCount());
        assertEquals(4, parsed.size());
    }

    @Test
    public void parse_pathNotStartingWithSlash() throws Exception {
        thrown.expect(IllegalArgumentException.class);
        PathPattern parsed = PathPattern.parse("parameter");
    }

    @Test
    public void parse_parameterWithSeparatorButNoRegex() throws Exception {
        thrown.expect(IllegalArgumentException.class);
        PathPattern parsed = PathPattern.parse("/{parameter:}");
    }

}
