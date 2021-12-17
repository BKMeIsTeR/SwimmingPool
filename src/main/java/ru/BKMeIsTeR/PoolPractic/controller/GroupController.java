package ru.BKMeIsTeR.PoolPractic.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.BKMeIsTeR.PoolPractic.DTO.GroupDto;
import ru.BKMeIsTeR.PoolPractic.DTO.GroupScheduleDto;
import ru.BKMeIsTeR.PoolPractic.DTO.InstructorDtoResponse;
import ru.BKMeIsTeR.PoolPractic.entity.GroupEntity;
import ru.BKMeIsTeR.PoolPractic.service.GroupService;

import java.util.List;

/**
 * Класс-котроллер для групп
 * @author BKMeIsTeR
 */
@Controller
@PreAuthorize("hasRole('ADMIN')")
@RequestMapping("/groups")
public class GroupController {
    @Autowired
    private GroupService groupService;

    @Autowired
    public GroupController(GroupService groupService) {
        this.groupService = groupService;
    }

    /**
     * Вывод всех групп
     * @return список всех групп
     */
    @GetMapping
    @ResponseBody
    public List<GroupEntity> allGroups() {
        return groupService.allGroups();
    }

    /**
     * Добавление группы
     * @param groupDto - объект передачи данных группы
     * @return сообщение о добавлении группы
     */
    @PostMapping
    public ResponseEntity<String> addGroup(@RequestBody GroupDto groupDto) {
        groupService.addGroup(groupDto);

        return new ResponseEntity<>("Группа " + groupDto.getName() + " была добавлена", HttpStatus.ACCEPTED);
    }

    /**
     * Добавление занятия в группу
     */
    @PostMapping("/{group_id}/schedule")
    public ResponseEntity<String> addGroupSchedule(@RequestBody GroupScheduleDto groupScheduleDto,
                                                   @PathVariable(value = "group_id") Long groupId) {
        groupService.addGroupSchedule(groupId, groupScheduleDto);

        return new ResponseEntity<>("Занятие на " + groupScheduleDto.getDate() + " было добавлено", HttpStatus.ACCEPTED);
    }

    /**
     * Вывод расписания занятий группы
     */
    @GetMapping("/{group_id}/schedule")
    @ResponseBody
    public List<GroupScheduleDto> showGroupSchedule(@PathVariable(value = "group_id") Long groupId) {
        return groupService.showGroupSchedule(groupId);
    }

    /**
     * Удаление группы
     * @param groupId - id группы для удаления
     * @return сообщение о успешном удалении группы
     */
    @DeleteMapping("/{group_id}")
    public ResponseEntity<String> deleteGroup(@PathVariable(value = "group_id") Long groupId) {
        groupService.deleteGroup(groupId);

        return new ResponseEntity<>("Группа была успешно удалена", HttpStatus.ACCEPTED);
    }

    /**
     * Вывод списка инструкторов, с применением фильтра
     * (доступа инф-ия о текущей занятости, пересечение времени группы с желаемым и без перегруженных инструктаров)
     * @param groupId - id группы для удаления
     * @return список инструкторов с применением фильтра
     */
    @GetMapping("/{group_id}/{number_week}")
    @ResponseBody
    public List<InstructorDtoResponse> showInstructorByFilter(@PathVariable(value = "number_week") int weekNumber, @PathVariable(value = "group_id") Long groupId) {
        return groupService.showInstructorByFilter(groupId ,weekNumber);
    }

    /**
     * Изменение инструктора в группе
     * @param groupId - id Группы
     * @param instructorId - id инструктора
     * @return сообщение о успешном изменении инструктора
     */
    @PutMapping("/{group_id}/{instructor_id}")
    public ResponseEntity<String> assigningInstructorGroup(
            @PathVariable(value = "group_id") Long groupId,
            @PathVariable(value = "instructor_id") Long instructorId) {
        groupService.assigningInstructorGroup(groupId, instructorId);

        return new ResponseEntity<>("Инструктор установлен в указанную группу", HttpStatus.ACCEPTED);
    }

    /**
     * Удаление инструктора из группы
     * @param groupId - id Группы
     * @param instructorId - id инструктора
     * @return сообщение о успешном удалении инструктора
     */
    @DeleteMapping("/{group_id}/{instructor_id}")
    public ResponseEntity<String> deleteInstructorGroup(
            @PathVariable(value = "group_id") Long groupId,
            @PathVariable(value = "instructor_id") Long instructorId) {
        groupService.deleteInstructorGroup(groupId, instructorId);

        return new ResponseEntity<>("Инструктор был удален из группы", HttpStatus.ACCEPTED);
    }
}