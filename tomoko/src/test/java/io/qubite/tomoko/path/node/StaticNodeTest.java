package io.qubite.tomoko.path.node;

import org.junit.Test;

import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.assertTrue;

public class StaticNodeTest {

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

    @Test(expected = IllegalArgumentException.class)
    public void constructor_slashCharacter() throws Exception {
        new StaticNode("/asdf");
    }
}
