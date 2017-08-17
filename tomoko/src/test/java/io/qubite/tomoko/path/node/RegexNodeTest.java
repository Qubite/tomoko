package io.qubite.tomoko.path.node;

import org.junit.Test;

import java.util.regex.Pattern;

import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.assertTrue;

public class RegexNodeTest {

    @Test
    public void doesMatch_matchingString() throws Exception {
        RegexNode node = new RegexNode(Pattern.compile("^[asdf]+$"));
        assertTrue(node.doesMatch("asdffdsassdddffdasddd"));
    }

    @Test
    public void doesMatch_notMatchingString() throws Exception {
        RegexNode node = new RegexNode(Pattern.compile("^[asdf]+$"));
        assertFalse(node.doesMatch("q"));
    }

}
