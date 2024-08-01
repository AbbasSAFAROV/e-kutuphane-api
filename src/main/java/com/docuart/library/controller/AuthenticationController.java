package com.docuart.library.controller;


import com.docuart.library.entity.User;
import com.docuart.library.repository.UserRepository;
import com.docuart.library.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "http://localhost:3000")
public class AuthenticationController {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserRepository userRepository;
    private PasswordEncoder passEncoder = new BCryptPasswordEncoder();
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User authRequest) throws Exception {
        User user = userRepository.findByUsername(authRequest.getUsername()).orElseThrow(()-> new RuntimeException("Kullanici bulunamadi ..."));
        String jwt =jwtUtil.generateToken(user.getUsername(),null);

        Boolean isMatch = passEncoder.matches(authRequest.getUserPassword(), user.getUserPassword());
        if (!isMatch) {
            throw new Exception("Kullanıcı adı veya parola hatalı");
        }
        user.setToken(jwt);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

//    function handleLogoutClick() {
//        localStorage.removeItem(userKey);
//        localStorage.removeItem(usersKey);
//        localStorage.removeItem(vekilKey);
//        axiosData.user = null;
//        deleteSubjects();
//        history.push('/login');
//    }

}
