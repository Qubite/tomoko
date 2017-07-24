package io.qubite.tomoko.jackson;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.qubite.tomoko.PatcherException;
import io.qubite.tomoko.patch.ValueTree;
import io.qubite.tomoko.type.Types;
import org.junit.Before;
import org.junit.Test;

import java.io.InputStream;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class JacksonTreeTest {

    private NodeFactory factory;

    @Before
    public void before() {
        factory = new NodeFactory(new ObjectMapper());
    }

    @Test
    public void getAs_string() throws Exception {
        ValueTree tree = factory.toTree(fromFile("/trees/stringValue.json"));
        String result = tree.getAs(Types.string());
        assertEquals("asdf", result);
    }

    @Test(expected = PatcherException.class)
    public void getAs_stringAsDouble_exception() throws Exception {
        ValueTree tree = factory.toTree(fromFile("/trees/stringValue.json"));
        tree.getAs(Types.doubleValue());
    }

    @Test
    public void getAs_stringList() throws Exception {
        ValueTree tree = factory.toTree(fromFile("/trees/stringList.json"));
        List<String> result = tree.getAs(Types.list(String.class));
        assertEquals(2, result.size());
    }

    @Test
    public void fieldIterator_complexObject() throws Exception {
        ValueTree tree = factory.toTree(fromFile("/trees/complexObject.json"));
        Iterator<Map.Entry<String, ValueTree>> iterator = tree.getFieldIterator();
        Map.Entry<String, ValueTree> titleNode = iterator.next();
        Map.Entry<String, ValueTree> authorNode = iterator.next();
        assertEquals("title", titleNode.getKey());
        assertEquals("author", authorNode.getKey());
        Iterator<Map.Entry<String, ValueTree>> authorNodeIterator = authorNode.getValue().getFieldIterator();
        Map.Entry<String, ValueTree> firstNameNode = authorNodeIterator.next();
        Map.Entry<String, ValueTree> familyNameNode = authorNodeIterator.next();
        assertEquals("Brzęczyszczykiewicz", familyNameNode.getValue().getAs(Types.string()));
    }

    private InputStream fromFile(String resourceName) {
        return getClass().getResourceAsStream(resourceName);
    }

}
