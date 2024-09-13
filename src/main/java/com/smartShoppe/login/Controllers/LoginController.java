package com.smartShoppe.login.Controllers;

import com.smartShoppe.login.Dto.UserDto;
import com.smartShoppe.login.Services.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RestController
@RequestMapping(path = "/login")
public class LoginController {

    final LoginService loginService;

    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    @PostMapping(path = "/signin")
    public ResponseEntity<?> authenticateUser(@RequestBody UserDto userDto){
        if (Objects.isNull(userDto))
            return new ResponseEntity<>("No user name or Password", HttpStatus.PRECONDITION_FAILED);
        try {
            UserDto authenticatedUserDto = loginService.authenticateUser(userDto);
            return new ResponseEntity<>(authenticatedUserDto, HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping(path = "/signup")
    public ResponseEntity<?> addUser(@RequestBody UserDto userDto){
        if (Objects.isNull(userDto))
            return new ResponseEntity<>("No user name or Password", HttpStatus.PRECONDITION_FAILED);
        try {
            UserDto authenticatedUserDto = loginService.addUser(userDto);
            return new ResponseEntity<>(authenticatedUserDto, HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping(path = "/changePassword")
    public ResponseEntity<?> changeUserPassword(@RequestBody UserDto userDto){
        if (Objects.isNull(userDto))
            return new ResponseEntity<>("No user name or Password", HttpStatus.PRECONDITION_FAILED);
        try {
            Boolean isPasswordChanged = loginService.changeUserPassword(userDto);
            if (Boolean.TRUE.equals(isPasswordChanged))
                return ResponseEntity.ok("Password Successfull Changed");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found.");
        } catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
