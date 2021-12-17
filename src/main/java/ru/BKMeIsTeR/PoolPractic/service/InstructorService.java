package ru.BKMeIsTeR.PoolPractic.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ru.BKMeIsTeR.PoolPractic.DTO.InstructorDto;
import ru.BKMeIsTeR.PoolPractic.entity.GroupEntity;
import ru.BKMeIsTeR.PoolPractic.entity.InstructorEntity;
import ru.BKMeIsTeR.PoolPractic.exceptions.BaseExteption;
import ru.BKMeIsTeR.PoolPractic.repository.InstructorRepository;
import ru.BKMeIsTeR.PoolPractic.repository.UserRepository;

import java.util.List;

@Service
public class InstructorService {

    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private InstructorRepository instructorRepository;
    private UserRepository userRepository;

    @Autowired
    public InstructorService(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder, InstructorRepository instructorRepository) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.instructorRepository = instructorRepository;
    }

    /**
     * Вывод информации о инструкторе
     * @return информация о инструкторе
     */
    public List<InstructorEntity> showAllInstructors() {
        return instructorRepository.findAll();
    }

    /**
     * Поиск инструктора по Id
     * @param instructorId - id инструктора
     * @return информация о инструкторе или сообщение о несуществование искоемого
     */
    public InstructorEntity findInstructorById(Long instructorId) {
        return instructorRepository.findById(instructorId).orElseThrow(
                () -> new BaseExteption("Не удалось найти инструктора по id = " + instructorId));
    }

    /**
     * Добавление инструктора
     * @param instructorDto - объект передачи данных инструктора
     */
    public void addInstructor(InstructorDto instructorDto) {
        if (userRepository.findByUsername(instructorDto.getUserName()).isPresent())
            throw new BaseExteption("Пользователь с таким именем уже сущeствует");

        instructorDto.setPassword(bCryptPasswordEncoder.encode(instructorDto.getPassword()));

        instructorRepository.save(new InstructorEntity(instructorDto));
    }

    /**
     * Удаление инструктора
     * @param instructorId - id инструктора
     */
    public void deleteInstructor(Long instructorId) {
        InstructorEntity instructor = instructorRepository.findById(instructorId).orElseThrow(
                () -> new BaseExteption("Не удалось найти инструктора с таким id = " + instructorId));

        //Если за инструктором закреплены группы, то удаляем зависимости
        if (!instructor.getGroupEntities().isEmpty()) {

            for (GroupEntity ge : instructor.getGroupEntities())
                ge.setInstructorEntity(null);

            instructor.getGroupEntities().clear();

            instructorRepository.save(instructor);
        }

        instructorRepository.deleteById(instructorId);
    }
}
