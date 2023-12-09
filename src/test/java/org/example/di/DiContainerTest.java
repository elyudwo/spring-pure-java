package org.example.di;

import org.example.exercise.proxy.ProxyAirplane;
import org.example.exercise.proxy.TwayAirplane;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class DiContainerTest {

    @Test
    void 빈_등록_테스트() {
        DiContainer diContainer = getContainer();
        TwayAirplane twayAirplane = diContainer.getBean(TwayAirplane.class);
        ProxyAirplane proxyAirplane = diContainer.getBean(ProxyAirplane.class);

        assertThat(twayAirplane.fly()).isEqualTo("이륙합니다.");
        assertThat(proxyAirplane).isInstanceOf(ProxyAirplane.class);
    }

    DiContainer getContainer() {
        Set<Class<?>> beans = new HashSet<>();
        beans.add(TwayAirplane.class);
        beans.add(ProxyAirplane.class);

        return new DiContainer(beans);
    }

}