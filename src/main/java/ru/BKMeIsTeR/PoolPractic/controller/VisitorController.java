package ru.BKMeIsTeR.PoolPractic.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.BKMeIsTeR.PoolPractic.DTO.VisitorDto;
import ru.BKMeIsTeR.PoolPractic.entity.GroupEntity;
import ru.BKMeIsTeR.PoolPractic.entity.UserEntity;
import ru.BKMeIsTeR.PoolPractic.exceptions.BaseExteption;
import ru.BKMeIsTeR.PoolPractic.service.GroupService;
import ru.BKMeIsTeR.PoolPractic.service.VisitorService;

/**
 * Класс-котроллер для посетителей
 * @author BKMeIsTeR
 */
@Controller
public class VisitorController {
    private VisitorService visitorService;
    private GroupService groupService;

    @Autowired
    public VisitorController(VisitorService visitorService, GroupService groupService) {
        this.visitorService = visitorService;
        this.groupService = groupService;
    }

    /**
     * Добавить посетителя
     * @param visitorDto - объект передачи данных посетителя
     * @return информация о успешно добавленном посетителе
     */
    @PostMapping("/registration")
    public ResponseEntity<String> addVisitor(@RequestBody VisitorDto visitorDto) {
        if (!visitorDto.getPassword().equals(visitorDto.getPasswordConfirm())){
            return new ResponseEntity<>("Пароли не совпали", HttpStatus.CONFLICT);
        }

        visitorService.addVisitor(visitorDto);

        return new ResponseEntity<>(visitorDto.toString(), HttpStatus.ACCEPTED);
    }

    /**
     * Вывод информации указанной группы
     * @param groudId - id группы
     * @return  информация о группе
     */
    @GetMapping("users/{group_id}")
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseBody
    public GroupEntity findGroup(@PathVariable(value = "group_id") Long groudId) {
        return groupService.findGroup(groudId);
    }

    /**
     * Добавление посетителя в группу
     * @param groupId - id группы
     * @param user - информация о авторизованном пользователе
     * @return информация о успешном добавлении посетителя в указанную группу
     */
    @PostMapping("users/{group_id}")
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseBody
    public ResponseEntity<String> addVisitorToGroup(
            @PathVariable(value = "group_id") Long groupId,
            @AuthenticationPrincipal UserEntity user) {
        groupService.addVisitorGroup(groupId, user);

        return new ResponseEntity<>("Посетитель был успешно добавлен в группу", HttpStatus.ACCEPTED);
    }

    /**
     * Удаление посетителя из группы
     * @param groupId - id группы
     * @param user - информация о авторизованном пользователе
     * @return информация о успешном удалении посетителя из указанной группу
     */
    @DeleteMapping("users/{group_id}")
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseBody
    public ResponseEntity<String> deleteVisitorToGroup(
            @PathVariable(value = "group_id") Long groupId,
            @AuthenticationPrincipal UserEntity user) {
        groupService.deleteVisitorGroup(groupId, user);

        return new ResponseEntity<>("Посетитель успешно удален из группы", HttpStatus.ACCEPTED);
    }
}