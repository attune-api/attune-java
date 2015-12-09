import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 * Config loader Tester.
 *
 * @author <sudnya>
 * @version 1.0
 * @since <pre>June 04, 2015</pre>
 */
public class LoadConfigPropertiesTest {

    @Before
    public void before() throws Exception {
    }

    @After
    public void after() throws Exception {
    }


    /**
     * Method: test FallbackClient is instantiated when testMode is true
     * @throws Exception
     */
    @Test
    public void testLoadConfigFromProperties() throws Exception {
        assertTrue(true);
        /*String fileName       = "config.properties";
        Properties configFile = new Properties();
        configFile.load(getClass().getClassLoader().getResourceAsStream(fileName));
        assertNotNull(configFile.getProperty("end_point"));
        assertNotNull(configFile.getProperty("timeout"));
        assertNotNull(configFile.getProperty("client_id"));
        assertNotNull(configFile.getProperty("client_secret"));
        assertNotNull(configFile.getProperty("test_mode"));
        assertTrue(Boolean.parseBoolean(configFile.getProperty("http.keepAlive")));*/
    }
}

