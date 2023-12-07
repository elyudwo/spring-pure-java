package org.example.proxy;

public class ProxyAirplane implements Airplane {

    private TwayAirplane twayAirplane;

    @Override
    public String fly() {
        if(twayAirplane == null) {
            twayAirplane = new TwayAirplane();
        }
        return twayAirplane.fly();
    }
}
