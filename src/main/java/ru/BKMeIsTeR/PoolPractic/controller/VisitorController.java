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
import ru.BKMeIsTeR.PoolPractic.exceptions.GlobalExseptionHandler;
import ru.BKMeIsTeR.PoolPractic.exceptions.PeopleIncorrectData;
import ru.BKMeIsTeR.PoolPractic.service.GroupService;
import ru.BKMeIsTeR.PoolPractic.service.VisitorService;

@Controller
public class VisitorController {
    private VisitorService visitorService;
    private GroupService groupService;

    @Autowired
    public VisitorController(VisitorService visitorService, GroupService groupService) {
        this.visitorService = visitorService;
        this.groupService = groupService;
    }

    //Добавить посетителя
    @PostMapping("/registration")
    public ResponseEntity<String> addVisitor(@RequestBody VisitorDto visitorDto) {
        visitorService.addAdmin();

        if (!visitorDto.getPassword().equals(visitorDto.getPasswordConfirm())){
            return new ResponseEntity<>("Пароли не совпали", HttpStatus.CONFLICT);
        }

        visitorService.addVisitor(visitorDto);

        return new ResponseEntity<>(visitorDto.toString(), HttpStatus.ACCEPTED);
    }


    @PreAuthorize("hasAutority('ADMIN')")
    //Получить посетителей по группе
    @GetMapping("users/{group_id}")
    @ResponseBody
    public GroupEntity findGroup(@PathVariable(value = "group_id") Long groudId) {
        return groupService.findGroup(groudId);
    }





    //Добавление посетителя в группу
    @PostMapping("users/{group_id}")
    @ResponseBody
    public ResponseEntity<String> addVisitorToGroup(
            @PathVariable(value = "group_id") Long id,
            @AuthenticationPrincipal UserEntity user) {
        groupService.addVisitorGroup(id, user);

        return new ResponseEntity<>("Посетитель был успешно добавлен в группу", HttpStatus.ACCEPTED);
    }





    //Удаление посетителя из группы
    @DeleteMapping("users/{group_id}")
    @ResponseBody
    public ResponseEntity<String> deleteVisitorToGroup(
            @PathVariable(value = "group_id") Long id,
            @AuthenticationPrincipal UserEntity user) {
        groupService.deleteVisitorGroup(id, user);

        return new ResponseEntity<>("Посетитель успешно удален из группы", HttpStatus.ACCEPTED);
    }
}