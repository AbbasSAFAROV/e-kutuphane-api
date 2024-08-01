package com.docuart.library.controller;

import com.docuart.library.entity.UserBookDTO;
import com.docuart.library.service.UserBookServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/UserBook")
@CrossOrigin(origins = "http://localhost:3000")
public class UserBookController {

    @Autowired
    private UserBookServices userBookServices;

    @GetMapping
    public void assignBookToUser(@RequestParam Long userId, @RequestParam Long bookId){
         userBookServices.assignBookToUser(userId,bookId);
    }

    @GetMapping("/assignBook")
    public void assignBookToUserName(@RequestParam String username, @RequestParam Long bookId) {
        userBookServices.assignBookToUserName(username,bookId);
    }

    @GetMapping("/UserBookDTO")
    public List<UserBookDTO> getAllUserBooks() {
        return userBookServices.getAllUserBooks();
    }
}
