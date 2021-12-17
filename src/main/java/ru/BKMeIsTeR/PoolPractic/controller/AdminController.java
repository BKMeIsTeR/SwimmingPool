package ru.BKMeIsTeR.PoolPractic.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import ru.BKMeIsTeR.PoolPractic.entity.UserEntity;
import ru.BKMeIsTeR.PoolPractic.service.UserService;
import ru.BKMeIsTeR.PoolPractic.service.VisitorService;

import java.util.List;

/**
 * Класс-котроллер для администрации
 * @author BKMeIsTeR
 */
@Controller
public class AdminController {
    private UserService userService;
    private VisitorService visitorService;

    @Autowired
    public AdminController(UserService userService, VisitorService visitorService) {
        this.userService = userService;
        this.visitorService = visitorService;
    }

    /**
     * Вывод всех пользователей
     * @return список пользователей
     */
    @GetMapping("/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> userList1() {
        return new ResponseEntity<>(userService.allUsers().toString(), HttpStatus.ACCEPTED);
    }

    @GetMapping("/test")
    public ResponseEntity<String> test() {
        visitorService.addAdmin();
        visitorService.addUsersTest();

        return new ResponseEntity<>("Данные добавлены", HttpStatus.ACCEPTED);
    }
}