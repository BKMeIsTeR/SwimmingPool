package ru.BKMeIsTeR.PoolPractic.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ru.BKMeIsTeR.PoolPractic.entity.RoleEntity;
import ru.BKMeIsTeR.PoolPractic.entity.UserEntity;
import ru.BKMeIsTeR.PoolPractic.exceptions.BaseExteption;
import ru.BKMeIsTeR.PoolPractic.repository.RoleRepository;
import ru.BKMeIsTeR.PoolPractic.repository.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
public class UserService implements UserDetailsService{

    private UserRepository userRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UserService(UserRepository userRepository,
                       BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username).orElseThrow(
                () -> new BaseExteption("Пользовать не найден"));
    }

    public UserEntity findUserById(Long userId) {
        return userRepository.findById(userId).orElseThrow(
                () -> new BaseExteption("Пользователя с таким id не существует"));
    }

    public List<UserEntity> allUsers() {
        return userRepository.findAll();
    }

    public void saveUser(UserEntity user, Long numberRole) {
        UserEntity userFromDB = userRepository.findByUsername(user.getUsername()).orElseThrow(
                () -> new BaseExteption("Пользователь с таким логином уже существует"));

        user.setUserRole(new RoleEntity(numberRole, "ROLE_USER"));
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));

        userRepository.save(user);
    }



/*    public boolean deleteUser(Long userId) {
        if (userRepository.findById(userId).isPresent()) {
            userRepository.deleteById(userId);
            return true;
        }
        return false;
    }*/
}