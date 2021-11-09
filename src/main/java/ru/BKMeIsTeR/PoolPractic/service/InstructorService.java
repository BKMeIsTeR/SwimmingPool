package ru.BKMeIsTeR.PoolPractic.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ru.BKMeIsTeR.PoolPractic.DTO.InstructorDto;
import ru.BKMeIsTeR.PoolPractic.entity.InstructorEntity;
import ru.BKMeIsTeR.PoolPractic.entity.UserEntity;
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

    public List<InstructorEntity> showAllInstructors() {
        return instructorRepository.findAll();
    }

    public InstructorEntity findInstructorById(Long id) {
        return instructorRepository.findById(id).orElseThrow(
                () -> new BaseExteption("Не удалось найти инструктора по id = " + id));
    }

    public void addInstructor(InstructorDto instructorDto) {
        UserEntity userFromDB = userRepository.findByUsername(instructorDto.getUserName()).orElseThrow(
                () -> new BaseExteption("Пользователь с таким логином уже существует"));

        instructorDto.setPassword(bCryptPasswordEncoder.encode(instructorDto.getPassword()));

        InstructorEntity instructor = new InstructorEntity(instructorDto);

        instructorRepository.save(instructor);
    }

    public void deleteInstructor(Long instructorId) {
        InstructorEntity instructor = instructorRepository.findById(instructorId).orElseThrow(
                () -> new BaseExteption("Не удалось найти инструктора с таким id = " + instructorId));


        if (!instructor.getGroupEntities().isEmpty()) {

            instructor.resetGroups();

            instructorRepository.save(instructor);
        }

        instructorRepository.deleteById(instructorId);
    }
}
