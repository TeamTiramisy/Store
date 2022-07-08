package com.dmdev.store;

import com.dmdev.store.annotation.IT;
import org.junit.jupiter.api.BeforeAll;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.testcontainers.containers.PostgreSQLContainer;

@IT
@Sql({
        "classpath:sql/data.sql"
})
@WithMockUser(username = "ruslankarina1.2@gmail.com", password = "test", authorities = {"ADMIN", "USER"})
public abstract class StoreTest {

    private static final PostgreSQLContainer<?> container = new PostgreSQLContainer<>("postgres:14.4");

    @BeforeAll
    static void runContainer() {
        container.start();
    }

    @DynamicPropertySource
    static void postgresProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", container::getJdbcUrl);
    }
}
