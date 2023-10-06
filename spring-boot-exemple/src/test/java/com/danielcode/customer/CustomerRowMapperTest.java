package com.danielcode.customer;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.ResultSet;
import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class CustomerRowMapperTest {

    private CustomerRowMapper underTest;
    private AutoCloseable autoCloseable;
    @Mock
    private ResultSet rs;

    @BeforeEach
    void setUp() {
        underTest = new CustomerRowMapper();
        autoCloseable = MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    void tearDown() throws Exception {
        autoCloseable.close();
    }

    @Test
    void mapRow() throws SQLException {
        // GIVEN
        int rowNum = 1;

        // WHEN
        when(rs.getInt("id")).thenReturn(1);
        when(rs.getString("name")).thenReturn("test");
        when(rs.getString("email")).thenReturn("test@test.gmail");
        when(rs.getInt("age")).thenReturn(22);

        Customer actual = underTest.mapRow(rs, rowNum);
        // THEN

        assert actual != null;
        assertThat(actual.getId()).isEqualTo(1);
        assertThat(actual.getName()).isEqualTo("test");
        assertThat(actual.getEmail()).isEqualTo("test@test.gmail");
        assertThat(actual.getAge()).isEqualTo(22);
    }
}