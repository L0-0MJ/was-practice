package org.example;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class QueryStringTest {
    @Test
    void createTest() {
        QueryString queryString = new QueryString("operand1", "11"); //1개에 대한 객체

        assertThat(queryString).isNotNull(); //assertThat(테스트 타켓).메소드1().메소드2().메소드3();
    }
}
