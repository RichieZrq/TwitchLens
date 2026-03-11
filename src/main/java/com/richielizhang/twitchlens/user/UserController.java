package com.richielizhang.twitchlens.user;


import com.richielizhang.twitchlens.model.RegisterBody;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
public class UserController {


    private final UserService userService;


    public UserController(UserService userService) {
        this.userService = userService;
    }


    @PostMapping("/register")
    @ResponseStatus(value = HttpStatus.OK)
    public void register(@RequestBody RegisterBody body) {
        if (body.username() == null || body.username().isBlank() ||
            body.password() == null || body.password().length() < 6) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid username or password");
        }
        userService.register(body.username(), body.password(), body.firstName(), body.lastName());
    }
}
