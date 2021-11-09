package ru.BKMeIsTeR.PoolPractic.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.BKMeIsTeR.PoolPractic.DTO.InstructorDto;
import ru.BKMeIsTeR.PoolPractic.DTO.InstructorDtoResponse;
import ru.BKMeIsTeR.PoolPractic.entity.InstructorEntity;
import ru.BKMeIsTeR.PoolPractic.service.InstructorService;

import java.util.List;

@Controller
@RequestMapping("/instructors")
public class InstructorContoller {
    private InstructorService instructorService;

    @Autowired
    public InstructorContoller(InstructorService instructorService) {
        this.instructorService = instructorService;
    }

    //Показать все группы
    @GetMapping
    @ResponseBody
    public List<InstructorEntity> allGroups() {
        return instructorService.showAllInstructors();
    }

    @PreAuthorize("hasAutority('INSTRUCTOR')")
    //Показать все группы, инструктора
    @GetMapping("/{id}")
    @ResponseBody
    public InstructorEntity allGroups(@PathVariable Long id) {
        return instructorService.findInstructorById(id);
    }

    //добавить инструктора
    @PostMapping("/registration")
    public ResponseEntity<String> addInstructor(@RequestBody InstructorDto instdto) {
        if (!instdto.getPassword().equals(instdto.getPasswordConfirm())) {
            return new ResponseEntity<>("Пароли не совпали", HttpStatus.CONFLICT);
        }

        instructorService.addInstructor(instdto);

        return new ResponseEntity<>(instdto.toString(), HttpStatus.ACCEPTED);
    }

    //Удаление инструктора
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteInstructor(@PathVariable Long id) {
        instructorService.deleteInstructor(id);

        return new ResponseEntity<>("Удаление прошло успешно", HttpStatus.ACCEPTED);
    }
}
