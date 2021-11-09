package ru.BKMeIsTeR.PoolPractic.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import ru.BKMeIsTeR.PoolPractic.service.UserService;

@Controller
public class AdminController {
    private UserService userService;

    @Autowired
    public AdminController(UserService userService) {
        this.userService = userService;
    }

    //Вывод всех пользователей
    @GetMapping("/admin")
    public ResponseEntity<String> userList1() {
        return new ResponseEntity<>(userService.allUsers().toString(), HttpStatus.ACCEPTED);
    }
}
