package ki.oprysko.web.controller;

import ki.oprysko.service.BlackListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Role;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
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
    private final BlackListService blackListService;

    @Autowired
    public UserController(UserService userService, SecurityService securityService, UserRegistrationValidator userRegistrationValidator, UserLoginValidator userLoginValidator, BlackListService blackListService) {
        this.userService = userService;
        this.securityService = securityService;
        this.userRegistrationValidator = userRegistrationValidator;
        this.userLoginValidator = userLoginValidator;
        this.blackListService = blackListService;
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
    public void logout(HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
    }

    @PreAuthorize(value = "hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    @GetMapping(value = "/all")
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }


    @PostMapping(value = "/ban/{userId}")
    public void addUserToBlackList(@PathVariable int userId) {
        User user = userService.findById(userId);
        if (!this.blackListService.isBlackListPerson(user.getId())) {
            blackListService.addUser(user);
        }
    }
}
