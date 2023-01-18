package com.juliocfleal.pessoacidade.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.juliocfleal.pessoacidade.entities.Person;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long>{

}
