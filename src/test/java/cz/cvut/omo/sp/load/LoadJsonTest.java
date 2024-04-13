package cz.cvut.omo.sp.load;

import junit.framework.TestCase;
import cz.cvut.omo.sp.manager.SmartHomeManager;

public class LoadJsonTest extends TestCase {

    public void testGetHomeConfiguration() {
        LoadJson loadJson = new LoadJson();
        SmartHomeManager smartHomeManager = loadJson.getHomeConfiguration();
        assertNotNull("SmartHomeManager should not be null", smartHomeManager);
    }
}
