package com.example.restsafeplatform.example;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/example")
public class ExampleController {

    @GetMapping
    public ResponseEntity getExample () {
        try{
            return ResponseEntity.ok("Сервер работает");
        } catch (Exception e){
            return ResponseEntity.badRequest().body("Ошибка");
        }
    }

}
