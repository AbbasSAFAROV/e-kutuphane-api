package com.docuart.library.service;
import com.docuart.library.entity.Book;
import com.docuart.library.entity.User;
import com.docuart.library.entity.UserBook;
import com.docuart.library.repository.BookRepository;
import com.docuart.library.repository.UserBookRepository;
import com.docuart.library.utils.GGUtils;
import com.docuart.library.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServices {


    @Autowired
    private UserRepository userRepository;

    public List<User> findAll(){
        return userRepository.findAll();
    }

    public User add(User user){
        return userRepository.save(user);
    }

    public User update(Long userId,User user){
        User guncellenecekUser = userRepository.findById(userId).orElseThrow(()->new RuntimeException("İd bulunamadı."));
        GGUtils.copyNonNullProperties(user,guncellenecekUser);
        return userRepository.save(guncellenecekUser);
    }

    public String delete(Long userId){
        User silinecekUser = userRepository.findById(userId).orElseThrow(()->new RuntimeException("İd bulunamadı."));
        userRepository.delete(silinecekUser);
        return "Silme işlemi başarılı.";
    }

    public User login(User user){
        Optional<User> user1 = userRepository.findByUsername(user.getUsername());
        if (user1.isPresent()) {
            if ((user1.get().getUsername().equals(user.getUsername()) && (user1.get().getUserPassword().equals(user.getUserPassword())))) {
                return user;
            } else {
                throw new RuntimeException("Geçersiz kullanici adı ve şifre");
            }
        } else {
            throw new RuntimeException("Kullanici bulunamadi ...");
        }

    }



}


