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

    //Добавить посетителя
    public void addVisitor(VisitorDto visitorDto) {
        UserEntity userFromDB = userRepository.findByUsername(visitorDto.getUserName()).orElseThrow(
                () -> new BaseExteption("Пользователь с таким именем уже сущeствует"));

        visitorDto.setPassword(bCryptPasswordEncoder.encode(visitorDto.getPassword()));

        VisitorEntity visitor = new VisitorEntity(visitorDto);

        visitorRepository.save(visitor);
    }





    //Добавить админа в БД
    public void addAdmin() {
        UserEntity admin = new UserEntity("admin", bCryptPasswordEncoder.encode("admin"));
        admin.setUserRole(new RoleEntity(3L, "ROLE_ADMIN"));

        userRepository.save(admin);
    }
}

