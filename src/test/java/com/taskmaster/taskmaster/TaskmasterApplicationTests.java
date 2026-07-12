package com.taskmaster.taskmaster;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("local")
class TaskmasterApplicationTests {

    @Test
    void contextLoads() {
        // Verifies the Spring application context starts successfully.
    }
}
