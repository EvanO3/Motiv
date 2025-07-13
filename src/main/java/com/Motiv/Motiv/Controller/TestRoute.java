package com.Motiv.Motiv.Controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/api/workout")
public class TestRoute {
    
    
    @GetMapping("/pull")
    public ResponseEntity<String> getWorkout(){
        return new ResponseEntity<>("Push Pull Legs", HttpStatus.ACCEPTED);
    }
    
}
