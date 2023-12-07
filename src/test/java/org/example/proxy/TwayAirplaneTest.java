package org.example.proxy;

import org.junit.jupiter.api.Test;
import org.assertj.core.api.Assertions;

class TwayAirplaneTest {

    @Test
    void fly() {
        // given
        TwayAirplane twayAirplane = new TwayAirplane();

        // when
        String msg = twayAirplane.fly();

        // then
        Assertions.assertThat(msg).isEqualTo("이륙합니다.");
    }
}