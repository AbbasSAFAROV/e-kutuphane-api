package com.docuart.library.service;

import com.docuart.library.entity.Book;
import com.docuart.library.entity.User;
import com.docuart.library.entity.UserBook;
import com.docuart.library.entity.UserBookDTO;
import com.docuart.library.repository.BookRepository;
import com.docuart.library.repository.UserBookRepository;
import com.docuart.library.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserBookServices {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private UserBookRepository userBookRepository;

    @Transactional
    public void assignBookToUser(Long userId, Long bookId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("Kullanıcı İd bulunamadı."));
        Book book = bookRepository.findById(bookId).orElseThrow(() -> new RuntimeException("Kitap İd bulunamadı."));

        if (book.getQuantity() > 1) {
            UserBook userBook = new UserBook();
            userBook.setUser(user);
            userBook.setBook(book);
            userBookRepository.save(userBook);

            book.setQuantity(book.getQuantity() - 1);
            bookRepository.save(book);

        } else {
            throw new RuntimeException("Kitap yok");
        }


    }
    @Transactional
    public void assignBookToUserName(String username, Long bookId) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Kullanıcı adı bulunamadı."));
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new RuntimeException("Kitap İd bulunamadı."));

        if (book.getQuantity() > 1) {
            UserBook userBook = new UserBook();
            userBook.setUser(user);
            userBook.setBook(book);
            userBookRepository.save(userBook);

            book.setQuantity(book.getQuantity() - 1);
            bookRepository.save(book);
        } else {
            throw new RuntimeException("Kitap yok");
        }
    }

    public List<UserBookDTO> getAllUserBooks() {
        List<UserBook> userBooks = userBookRepository.findAll();
        return userBooks.stream()
                .map(userBook -> new UserBookDTO(userBook.getUser().getUsername(), userBook.getBook().getBookName()))
                .collect(Collectors.toList());
    }



}
