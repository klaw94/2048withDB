package com.example.spring.dao;

import com.example.spring.model.Person;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;


import javax.swing.*;
import java.sql.*;
import java.util.List;
import java.util.UUID;

@Qualifier("postgres")
@Repository
public class PersonDataAccessService implements PersonDao {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public PersonDataAccessService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public int insertPerson(Person person) {
        jdbcTemplate.update(
                "INSERT INTO person(name, score) VALUES (?, ?)",
                person.getName(), person.getScore()
        );
        return 0;
    }


    @Override
    public List<Person> selectAllPeople() {
        final String sql = "SELECT * FROM person";

        return jdbcTemplate.query(sql, (resultSet, i) -> {
            String name = resultSet.getString("name");
            int score = resultSet.getInt("score");
            return new Person(name, score);
        });
    }

}

