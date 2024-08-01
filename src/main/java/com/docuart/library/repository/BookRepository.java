package com.docuart.library.repository;

import com.docuart.library.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    @Query(value = "" +
            "" +
            " SELECT * from book t where t.quantity > 0 " +
            " order by book_id desc ", nativeQuery = true)
    List<Book> findAllBooks();

}
