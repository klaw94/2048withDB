package com.example.spring.dao;

import com.example.spring.model.Person;

import java.util.List;


public interface PersonDao {

    default int insertPerson(Person person){
        return insertPerson(person);
    }

    List<Person> selectAllPeople();

}
