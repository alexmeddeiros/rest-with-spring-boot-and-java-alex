package br.com.alex.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.alex.model.Book;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

}
