package ru.BKMeIsTeR.PoolPractic.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ru.BKMeIsTeR.PoolPractic.DTO.VisitorDto;
import ru.BKMeIsTeR.PoolPractic.entity.RoleEntity;
import ru.BKMeIsTeR.PoolPractic.entity.UserEntity;
import ru.BKMeIsTeR.PoolPractic.entity.VisitorEntity;
import ru.BKMeIsTeR.PoolPractic.exceptions.BaseExteption;
import ru.BKMeIsTeR.PoolPractic.repository.UserRepository;
import ru.BKMeIsTeR.PoolPractic.repository.VisitorRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class VisitorService {

    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private VisitorRepository visitorRepository;
    private UserRepository userRepository;

    @Autowired
    public VisitorService(BCryptPasswordEncoder bCryptPasswordEncoder, VisitorRepository visitorRepository, UserRepository userRepository) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.visitorRepository = visitorRepository;
        this.userRepository = userRepository;
    }

    /**
     * Регистрация посетителя
     * @param visitorDto - объект передачи данных посетителя
     */
    public void addVisitor(VisitorDto visitorDto) {
        if (userRepository.findByUsername(visitorDto.getUserName()).isPresent())
            throw new BaseExteption("Пользователь с таким именем уже сущeствует");

        visitorDto.setPassword(bCryptPasswordEncoder.encode(visitorDto.getPassword()));

        visitorRepository.save(new VisitorEntity(visitorDto));
    }
}