package ru.nessing.test_task.services;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.nessing.test_task.entities.Role;
import ru.nessing.test_task.entities.User;
import ru.nessing.test_task.repositories.UserRepository;

import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    public String createUser(String name, String password) {
        User user = new User();
        user.setId(null);
        user.setName(name);
        user.setPassword(passwordEncoder.encode(password));
        user.setRole(Role.USER);
        if (userRepository.findUserByName(name).isEmpty()) {
            userRepository.save(user);
            return "Вы успешно зарегистрировались";
        } else {
            return "Пользователь с именем " + name + " уже существует!";
        }
    }

    public User getUserByName(String name) {
        return userRepository.getUserByName(name).get();
    }

    public Boolean isEmptyName(String name) {
        return userRepository.findUserByName(name).isEmpty();
    }

    public Boolean checkLogin(User user) {
        int oneHour = 3600;
        int attempts = 10;

        if (!isEmptyName(user.getName())) {
            User userFromDB = getUserByName(user.getName());

            // установка стартавого времени
            if (userFromDB.getTimeCountStart() == null) {
                long timeStart = TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis());
                userFromDB.setTimeCountStart(timeStart);
            }
            // подсчет времени
            long diff = TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis());
            userFromDB.setTimeCountEnd(diff - userFromDB.getTimeCountStart());

            // сброс таймера и количество попыток
            if (userFromDB.getTimeCountEnd() != null && userFromDB.getTimeCountEnd() >= oneHour) {
                userFromDB.setTimeCountStart(null);
                userFromDB.setAttempt(0);
            }
            // превышение количества попыток И время не достигло 1 часа (10 попыток в 1 час)
            if (userFromDB.getAttempt() != null &&
                    userFromDB.getTimeCountEnd() != null &&
                    userFromDB.getAttempt() >= attempts &&
                    userFromDB.getTimeCountEnd() <= oneHour) {
                userFromDB.setAttempt(userFromDB.getAttempt() + 1);
                userRepository.save(userFromDB);
                return false;
            }

            boolean result = passwordEncoder.matches(user.getPassword(), userFromDB.getPassword());

            if (!result) {
                if (userFromDB.getTimeCountStart() == null) {
                    long timeStart = TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis());
                    userFromDB.setTimeCountStart(timeStart);
                }
                diff = TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis());
                userFromDB.setTimeCountEnd(diff - userFromDB.getTimeCountStart());
                userFromDB.setAttempt(userFromDB.getAttempt() + 1);
                userRepository.save(userFromDB);
            }

            userFromDB.setTimeCountStart(null);
            userFromDB.setAttempt(0);
            return result;
        }
        return false;
    }
}
