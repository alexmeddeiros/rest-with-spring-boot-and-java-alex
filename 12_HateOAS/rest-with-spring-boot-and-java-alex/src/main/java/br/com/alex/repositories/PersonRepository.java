package br.com.alex.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.alex.model.Person;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {

}
