package ru.BKMeIsTeR.PoolPractic.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.BKMeIsTeR.PoolPractic.DTO.GroupDto;
import ru.BKMeIsTeR.PoolPractic.DTO.GroupScheduleDto;
import ru.BKMeIsTeR.PoolPractic.DTO.InstructorDtoResponse;
import ru.BKMeIsTeR.PoolPractic.entity.*;
import ru.BKMeIsTeR.PoolPractic.exceptions.BaseExteption;
import ru.BKMeIsTeR.PoolPractic.repository.GroupRepository;
import ru.BKMeIsTeR.PoolPractic.repository.GroupScheduleRepository;
import ru.BKMeIsTeR.PoolPractic.repository.InstructorRepository;
import ru.BKMeIsTeR.PoolPractic.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class GroupService {
    private GroupRepository groupRepository;
    private InstructorRepository instructorRepository;
    private GroupScheduleRepository groupScheduleRepository;
    private UserRepository userRepository;

    @Autowired
    public GroupService(GroupRepository groupRepository, InstructorRepository instructorRepository, GroupScheduleRepository groupScheduleRepository, UserRepository userRepository) {
        this.groupRepository = groupRepository;
        this.instructorRepository = instructorRepository;
        this.groupScheduleRepository = groupScheduleRepository;
        this.userRepository = userRepository;
    }

    public List<GroupEntity> allGroups() {
        return groupRepository.findAll();
    }

    public void addGroup(GroupDto groupDto) {
        groupRepository.save(new GroupEntity(groupDto));
    }

    public GroupEntity findGroup(Long id) {
        return groupRepository.findById(id).orElseThrow(
                () -> new BaseExteption("Не удалось найти группу по id = " + id));
    }

    //Изменение инструктора в группе
    public void assigningInstructorGroup(Long groupId, Long instructorId) {
        GroupEntity group = groupRepository.findById(groupId).orElseThrow(
                () -> new BaseExteption("Не удалось найти группу по id = " + groupId));
        InstructorEntity instructor = instructorRepository.findById(instructorId).orElseThrow(
                () -> new BaseExteption("Не удалось найти инструктора по id = " + instructorId));

        //Установить инструктора в группу
        group.setInstructorEntity(instructor);

        groupRepository.save(group);
    }

    //Удаление инструктора в группе
    public void deleteInstructorGroup(Long groupId, Long instructorId) {
        GroupEntity group = groupRepository.findById(groupId).orElseThrow(
                () -> new BaseExteption("Не удалось найти группу по id = " + groupId));

        if (group.getInstructorEntity() == null)
            throw new BaseExteption("В группе " + group.getName() + " нет инструктора");

        if (group.getInstructorEntity().getId() != instructorId)
            throw new BaseExteption("В группе " + group.getName() + " нет такого инструктора");

        //Установить инструктора в группу
        group.setInstructorEntity(null);

        groupRepository.save(group);
    }

    //Добавление посетителя в группу
    public void addVisitorGroup(Long groupId, UserEntity user) {
        GroupEntity group = groupRepository.findById(groupId).orElseThrow(
                () -> new BaseExteption("Не удалось найти группу по id = " + groupId));

        VisitorEntity visitor = userRepository.findVisitorEntityByUserId(user.getId());

        if (group.getVisitorEntityList().contains(visitor))
            throw new BaseExteption("Вы уже записаны в данную группу");

        int countSex = groupRepository.checkFreePlacesInGroupByGender(visitor.isSex());

        if (visitor.isSex()) {
            if (countSex >= group.getNumberMen())
                throw new BaseExteption("В группе достигнуто максимальное количество посетителей мужского пола");
        }
        else {
            if (countSex >= group.getNumberWomen())
                throw new BaseExteption("В группе достигнуто максимальное количество посетителей женского пола");
        }

        group.getVisitorEntityList().add(visitor);

        groupRepository.save(group);
    }

    public void deleteVisitorGroup(Long groupId, UserEntity user) {
        GroupEntity group = groupRepository.findById(groupId).orElseThrow(
                () -> new BaseExteption("Не удалось найти группу по id = " + groupId));

        VisitorEntity visitor = userRepository.findVisitorEntityByUserId(user.getId());

        if (!group.getVisitorEntityList().contains(visitor))
            throw new BaseExteption("Вы не находитесь в данной группе, чтобы СПИСЫВАТЬСЯ С НЕЁ!!!");

        group.getVisitorEntityList().remove(visitor);

        groupRepository.save(group);
    }

    public void deleteGroup(Long groupId) {
        GroupEntity group = groupRepository.findById(groupId).orElseThrow(
                () -> new BaseExteption("Не удалось найти группу по id = " + groupId));

        group.getVisitorEntityList().clear();
        group.getGroupScheduleEntities().clear(); // Добавил только что
        group.setInstructorEntity(null);

        groupRepository.save(group); //Нужно т.к. иначе ограничение ключа расписания не даст удалить

        groupRepository.delete(group);
    }

    public List<InstructorDtoResponse> showInstructorByFilter(Long groupId, int weekNumber) {
        return instructorRepository.showInstructorByFilter(groupId, weekNumber);
    }

    public void addGroupSchedule(Long groupId, GroupScheduleDto groupScheduleDto) {
        GroupEntity ge = this.findGroup(groupId);

        GroupScheduleEntity gse = new GroupScheduleEntity(groupScheduleDto);
        gse.setGroupEntity(ge);

        ge.getGroupScheduleEntities().add(gse);

        groupRepository.save(ge);
    }

    public List<GroupScheduleDto> showGroupSchedule(Long groupId) {
        return groupScheduleRepository.findAllByGroupEntityIdEquals(groupId);
    }
}