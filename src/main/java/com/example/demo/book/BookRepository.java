package com.example.demo.book;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

//responsible for data access, connect with JPA for easy access of the books.
@Repository
public interface BookRepository
        extends JpaRepository<Book, Long> {

        @Query("SELECT s FROM Book s WHERE s.bookName = ?1")
        Optional<Book> findBookBybookName(String bookName);

        @Query("SELECT s FROM Book s WHERE s.id = ?1")
        Optional<Book> findBookById(Long Id);

        @Query("SELECT s.bookQuantity FROM Book s WHERE s.id = ?1")
        Integer getQuantityById(Long id);

}
