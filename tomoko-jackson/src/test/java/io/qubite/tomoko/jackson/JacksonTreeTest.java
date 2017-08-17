package io.qubite.tomoko.jackson;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.qubite.tomoko.TomokoException;
import io.qubite.tomoko.patch.ValueTree;
import io.qubite.tomoko.type.Types;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class JacksonTreeTest {

    ObjectMapper mapper = new ObjectMapper();

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void getAs_string() throws Exception {
        JacksonTreeParser parser = JacksonTreeParser.instance(mapper);
        String result = parser.getAs(JacksonTree.of(fromFile("/trees/stringValue.json")), Types.string());
        assertEquals("asdf", result);
    }

    @Test
    public void getAs_stringAsDouble_exception() throws Exception {
        JacksonTreeParser parser = JacksonTreeParser.instance(mapper);
        thrown.expect(TomokoException.class);
        parser.getAs(JacksonTree.of(fromFile("/trees/stringValue.json")), Types.doubleValue());
    }

    @Test
    public void getAs_stringList() throws Exception {
        JacksonTreeParser parser = JacksonTreeParser.instance(mapper);
        List<String> result = parser.getAs(JacksonTree.of(fromFile("/trees/stringList.json")), Types.list(String.class));
        assertEquals(2, result.size());
    }

    @Test
    public void fieldIterator_complexObject() throws Exception {
        JacksonTree tree = JacksonTree.of(fromFile("/trees/complexObject.json"));
        Iterator<Map.Entry<String, ValueTree>> iterator = tree.getFieldIterator();
        Map.Entry<String, ValueTree> titleNode = iterator.next();
        Map.Entry<String, ValueTree> authorNode = iterator.next();
        assertEquals("title", titleNode.getKey());
        assertEquals("author", authorNode.getKey());
        Iterator<Map.Entry<String, ValueTree>> authorNodeIterator = authorNode.getValue().getFieldIterator();
        Map.Entry<String, ValueTree> firstNameNode = authorNodeIterator.next();
        Map.Entry<String, ValueTree> familyNameNode = authorNodeIterator.next();
        assertEquals("firstName", firstNameNode.getKey());
        assertEquals("familyName", familyNameNode.getKey());
    }

    private JsonNode fromFile(String resourceName) throws IOException {
        return mapper.readTree(getClass().getResourceAsStream(resourceName));
    }

}
