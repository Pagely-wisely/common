package com.pagely.common;

import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = CommonApplicationTests.TestConfig.class)
class CommonApplicationTests {

    @SpringBootApplication
    static class TestConfig {
    }

    @Test
    void contextLoads() {
    }
}
