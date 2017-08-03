package io.qubite.tomoko.scenarios;

import io.qubite.tomoko.ConfigurationException;
import io.qubite.tomoko.direct.DirectTomoko;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class TomokoConfigurationTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void scanConfiguration_duplicatePath_exception() throws Exception {
        DirectTomoko tomoko = DirectTomoko.instance();
        thrown.expect(ConfigurationException.class);
        tomoko.scanPatcher(new DuplicatePathSpecification());
    }

    @Test
    public void scanConfiguration_invalidParameterName_exception() throws Exception {
        DirectTomoko tomoko = DirectTomoko.instance();
        thrown.expect(ConfigurationException.class);
        tomoko.scanPatcher(new InvalidParameterNameSpecification());
    }

}
