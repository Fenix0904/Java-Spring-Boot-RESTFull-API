package ki.oprysko.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ki.oprysko.domain.User;
import ki.oprysko.service.SecurityService;
import ki.oprysko.service.UserService;
import ki.oprysko.validator.UserLoginValidator;
import ki.oprysko.validator.UserRegistrationValidator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;


@RestController
public class UserController {

    private final UserService userService;
    private final SecurityService securityService;
    private final UserRegistrationValidator userRegistrationValidator;
    private final UserLoginValidator userLoginValidator;

    @Autowired
    public UserController(UserService userService, SecurityService securityService, UserRegistrationValidator userRegistrationValidator, UserLoginValidator userLoginValidator) {
        this.userService = userService;
        this.securityService = securityService;
        this.userRegistrationValidator = userRegistrationValidator;
        this.userLoginValidator = userLoginValidator;
    }

    @PostMapping(value = "/registration")
    public ResponseEntity<Void> registration(@RequestBody User user, BindingResult bindingResult) {
        userRegistrationValidator.validate(user, bindingResult);
        if (bindingResult.hasErrors()) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        userService.save(user);
        securityService.autoLogin(user.getUsername(), user.getConfirmPassword());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping(value = "/login")
    public ResponseEntity<Void> login(@RequestBody User user, BindingResult bindingResult) {
        userLoginValidator.validate(user, bindingResult);
        if (bindingResult.hasErrors()) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        securityService.autoLogin(user.getUsername(), user.getPassword());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(value = "/out")
    public ResponseEntity<Void> logout(HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null){
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(value = "/all")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }
}
