package ru.nessing.test_task.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.nessing.test_task.entities.ErrorAndInfo;
import ru.nessing.test_task.entities.User;
import ru.nessing.test_task.repositories.ErrorAndInfoRepository;
import ru.nessing.test_task.services.UserService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final ErrorAndInfoRepository errorAndInfoRepository;

    @PostMapping("/create")
    public String createUser(@RequestBody User user, HttpServletRequest request) {
        String result = userService.createUser(user.getName(), user.getPassword());
        try {
            request.login(user.getName(), user.getPassword());
        } catch (ServletException e) {
            e.printStackTrace();
        }
        return result;
    }

    @PostMapping("/login")
    public ErrorAndInfo login(@RequestBody User user, HttpServletRequest request) {
        int oneHour = 3600;
        int attempts = 10;

        try {
            User userCheck = userService.getUserByName(user.getName());
            if (userService.checkLogin(user) && userCheck.getAttempt() < attempts) {
                request.login(user.getName(), user.getPassword());
                return errorAndInfoRepository.getErrorById(4L);
            } else if (userCheck.getAttempt() >= attempts) {
                return new ErrorAndInfo(5L, errorAndInfoRepository.getErrorById(5L).getInfo() +
                        " подождите " + (oneHour - userCheck.getTimeCountEnd()) + " сек");
            } else {
                return errorAndInfoRepository.getErrorById(1L);
            }
        } catch (ServletException e) {
            e.printStackTrace();
            return errorAndInfoRepository.getErrorById(6L);
        }
    }

    @PostMapping("/check_name")
    public ErrorAndInfo checkName(@RequestBody User user) {
        return userService.isEmptyName(user.getName())
                ? errorAndInfoRepository.getErrorById(2L)
                : errorAndInfoRepository.getErrorById(3L);
    }
}
