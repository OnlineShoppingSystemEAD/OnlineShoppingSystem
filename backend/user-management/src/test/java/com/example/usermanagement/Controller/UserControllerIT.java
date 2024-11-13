package com.example.usermanagement.Controller;

import com.example.usermanagement.Entity.UserProfile;
import com.example.usermanagement.Service.OurUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserControllerIT {
    @Autowired
    private OurUserDetailsService userService;
    
}
