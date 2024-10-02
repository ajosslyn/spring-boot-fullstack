package com.amigoscode.spring_boot_project;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class TestContainersTest extends AbstractTestContainer {

    @Test
    void canStartPostgreSQLContainer() {
        assertThat(postgreSQLContainer.isRunning()).isTrue();
        assertThat(postgreSQLContainer.isCreated()).isTrue();
    }
}
