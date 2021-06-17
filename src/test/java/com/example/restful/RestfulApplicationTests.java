package com.example.restful;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.sql.DataSource;

import java.sql.Connection;
import java.sql.SQLException;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
@Slf4j
class RestfulApplicationTests {
    @Autowired
    DataSource dataSource;

    @Test
    void checkIfApplicationCanConnectToDbTest(){
        assertThat(dataSource).isNotNull();

        try {
            Connection connection = dataSource.getConnection();
            assertThat(connection).isNotNull();
            assertThat(connection.getCatalog()).isEqualTo("payroll_db");
        } catch (SQLException throwable) {
            log.info("Error occurred while connecting to db --> {}", throwable.getMessage());
        }

    }

}
