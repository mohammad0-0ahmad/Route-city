package Testpackage;

import org.junit.Assert;
import routeCity.Network;

class NetworkTest {

    @org.junit.jupiter.api.Test
    void getNodeSymbolTest() {
        Network network = Network.getInstance();
        Assert.assertEquals("G1",network.getNodeSymbol(6));
    }
}