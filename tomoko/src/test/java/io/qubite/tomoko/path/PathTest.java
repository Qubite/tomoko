package io.qubite.tomoko.path;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.Arrays;

import static org.junit.Assert.*;

public class PathTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void getLength_empty() throws Exception {
        Path path = Path.empty();
        assertEquals(0, path.getLength());
        assertTrue(path.isEmpty());
    }

    @Test
    public void getLength_singleNode() throws Exception {
        Path path = Path.of("asdf");
        assertEquals(1, path.getLength());
        assertFalse(path.isEmpty());
    }

    @Test
    public void parse_doesNotStartWithSlash() throws Exception {
        thrown.expect(IllegalArgumentException.class);
        Path.parse("asdfasdf");
    }

    @Test
    public void parse_singleNode() throws Exception {
        Path path = Path.parse("/asdfasdf");
        assertEquals(1, path.getLength());
        assertEquals("asdfasdf", path.getValue(0));
    }

    @Test
    public void parse_emptyNode() throws Exception {
        Path path = Path.parse("/");
        assertEquals(1, path.getLength());
        assertEquals("", path.getValue(0));
    }

    @Test
    public void of_slashInNode() throws Exception {
        thrown.expect(IllegalArgumentException.class);
        Path.of("asdf", "asdf/asdf");
    }

    @Test
    public void of_slashInNodeList() throws Exception {
        String[] nodes = {"asdf", "asdf/asdf"};
        thrown.expect(IllegalArgumentException.class);
        Path.of(Arrays.asList(nodes));
    }

    @Test
    public void append() throws Exception {
        Path initialPath = Path.of("qwer");
        Path appended = initialPath.append("asdf");
        assertEquals(2, appended.getLength());
        assertEquals("asdf", appended.getValue(1));
    }

    @Test
    public void prepend() throws Exception {
        Path initialPath = Path.of("qwer");
        Path finalPath = initialPath.prepend("asdf");
        assertEquals(2, finalPath.getLength());
        assertEquals("asdf", finalPath.getValue(0));
    }

}
