package com.example.AppUser.Controller;

import com.example.AppUser.services.UserService;
import jakarta.mail.MessagingException;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.AppUser.models.ApplicationUser;
import com.example.AppUser.dtos.LoginResponseDTO;
import com.example.AppUser.dtos.RegistrationDTO;
import com.example.AppUser.services.AuthenticationService;

@RestController
@RequestMapping("/user/auth")
@CrossOrigin("*")
@AllArgsConstructor
public class AuthenticationController {

    private AuthenticationService authenticationService;
    private UserService userService;
    @PostMapping("/register")
    public ApplicationUser registerUser(@RequestBody RegistrationDTO body){
        return authenticationService.registerUser(body.getUsername(), body.getPassword());
    }
    
    @PostMapping("/login")
    public LoginResponseDTO loginUser(@RequestBody RegistrationDTO body){
        return authenticationService.loginUser(body.getUsername(), body.getPassword());
    }
    @PutMapping("/forget-password")
    public void forgetpassword(@RequestParam(name ="email") String email) throws MessagingException {
        userService.forgetpassword(email);
    }
    @PutMapping("/set-password")
    public  void setpassword(@RequestParam String email , @RequestHeader String newpassword) {
        userService.setpassword(email, newpassword);
    }
}   
