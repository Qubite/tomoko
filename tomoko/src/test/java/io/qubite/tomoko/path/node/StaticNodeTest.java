package io.qubite.tomoko.path.node;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.assertTrue;

public class StaticNodeTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void doesMatch_sameString() throws Exception {
        StaticNode node = new StaticNode("staticText");
        assertTrue(node.doesMatch("staticText"));
    }

    @Test
    public void doesMatch_differentString() throws Exception {
        StaticNode node = new StaticNode("staticText");
        assertFalse(node.doesMatch("different"));
    }

    @Test
    public void constructor_slashCharacter() throws Exception {
        thrown.expect(IllegalArgumentException.class);
        new StaticNode("/asdf");
    }
}
