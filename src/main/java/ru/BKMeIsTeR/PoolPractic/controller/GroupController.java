package ru.BKMeIsTeR.PoolPractic.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.BKMeIsTeR.PoolPractic.DTO.GroupDto;
import ru.BKMeIsTeR.PoolPractic.DTO.InstructorDtoResponse;
import ru.BKMeIsTeR.PoolPractic.entity.GroupEntity;
import ru.BKMeIsTeR.PoolPractic.service.GroupService;

import java.util.List;

@Controller
@RequestMapping("/groups")
public class GroupController {
    private GroupService groupService;

    @Autowired
    public GroupController(GroupService groupService) {
        this.groupService = groupService;
    }

    //Получение всех групп
    @GetMapping
    @ResponseBody
    public List<GroupEntity> allGroups() {
        return groupService.allGroups();
    }

    //Добавление группы
    @PostMapping
    public ResponseEntity<String> addGroup(@RequestBody GroupDto groupDto) {
        groupService.addGroup(groupDto);

        return new ResponseEntity<>("Группа " + groupDto.getName() + " была добавлена", HttpStatus.ACCEPTED);
    }

    //Удаление группы по id
    @DeleteMapping("/{group_id}")
    @ResponseBody
    public ResponseEntity<String> deleteGroup(@PathVariable(value = "group_id") Long groupId) throws Exception {
        groupService.deleteGroup(groupId);

        return new ResponseEntity<>("Группа была успешно удалена", HttpStatus.ACCEPTED);
    }






    //Показать инструкторов по фильтру (доступа инф-ия о текущей занятости, пересечение времени группы с желаемым
    //и без перегруженных инструктаров)
    @GetMapping("/{group_id}")
    @ResponseBody
    public List<InstructorDtoResponse> showInstructorByFilter(@PathVariable(value = "group_id") Long groupId) {
        return groupService.showInstructorByFilter(groupId);
    }




    //Изменение инструктора в группе
    @PutMapping("/{group_id}/{instructor_id}")
    public ResponseEntity<String> assigningInstructorGroup(
            @PathVariable(value = "group_id") Long groupId,
            @PathVariable(value = "instructor_id") Long instructorId) {
        groupService.assigningInstructorGroup(groupId, instructorId);

        return new ResponseEntity<>("Инструктор установлен в указанную группу", HttpStatus.ACCEPTED);
    }

    //Удаление инструктора из группы
    @DeleteMapping("/{group_id}/{instructor_id}")
    public ResponseEntity<String> deleteInstructorGroup(
            @PathVariable(value = "group_id") Long groupId,
            @PathVariable(value = "instructor_id") Long instructorId) {
        groupService.deleteInstructorGroup(groupId, instructorId);

        return new ResponseEntity<>("Инструктор был удален из группы", HttpStatus.ACCEPTED);
    }
}



//    @PreAuthorize("hasAutority('ADMIN')")
//Просмотр информации о группе по её id
//    @GetMapping("/{group_id}")
//    @ResponseBody
//    public GroupEntity findGroup(@PathVariable(value = "group_id") Long id) throws Exception {
//        return groupService.findGroup(id);
//    }