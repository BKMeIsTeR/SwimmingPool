package ru.BKMeIsTeR.PoolPractic.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.BKMeIsTeR.PoolPractic.DTO.InstructorDto;
import ru.BKMeIsTeR.PoolPractic.entity.InstructorEntity;
import ru.BKMeIsTeR.PoolPractic.service.InstructorService;

import java.util.List;

/**
 * Класс-котроллер для инструкторов
 * @author BKMeIsTeR
 */
@Controller
@PreAuthorize("hasRole('ADMIN')")
@RequestMapping("/instructors")
public class InstructorContoller {
    private InstructorService instructorService;

    @Autowired
    public InstructorContoller(InstructorService instructorService) {
        this.instructorService = instructorService;
    }

    /**
     * Вывод информации о всех инструкторах
     */
    @GetMapping
    @ResponseBody
    public List<InstructorEntity> showAllInstructors() {
        return instructorService.showAllInstructors();
    }

    /**
     * Добавление инструктора
     * @param instDto - объект передачи данных инструктора
     * @return информацию о успешно добавленном пользователе
     */
    @PostMapping("/registration")
    public ResponseEntity<String> addInstructor(@RequestBody InstructorDto instDto) {
        if (!instDto.getPassword().equals(instDto.getPasswordConfirm())) {
            return new ResponseEntity<>("Пароли не совпали", HttpStatus.CONFLICT);
        }

        instructorService.addInstructor(instDto);

        return new ResponseEntity<>(instDto.toString(), HttpStatus.ACCEPTED);
    }

    /**
     * Вывод информации о инструкторе
     * @param intructorId - id инструктора
     * @return информация о инструкторе
     */
    @GetMapping("/{instructor_id}")
    @ResponseBody
    public InstructorEntity showInstructorById(@PathVariable(name = "instructor_id") Long intructorId) {
        return instructorService.findInstructorById(intructorId);
    }

    /**
     * Удаление инструктора
     * @param instructorId - id инструктора
     * @return сообщение о успешноп удалении инструктора
     */
    @DeleteMapping("/{instructor_id}")
    public ResponseEntity<String> deleteInstructor(@PathVariable(name = "instructor_id") Long instructorId) {
        instructorService.deleteInstructor(instructorId);

        return new ResponseEntity<>("Удаление прошло успешно", HttpStatus.ACCEPTED);
    }
}
