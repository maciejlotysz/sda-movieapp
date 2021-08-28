package com.maja.sdamovieapp.config;

import com.maja.sdamovieapp.containers.PostgresTestContainer;
import org.springframework.test.context.jdbc.Sql;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
@Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, scripts = "classpath:sql/delete.sql")
public class ContainersEnvironment {

    @Container
    public static PostgreSQLContainer postgreSQLContainer = PostgresTestContainer.getInstance();
}