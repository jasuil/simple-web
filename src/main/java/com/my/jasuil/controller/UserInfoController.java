package com.my.jasuil.controller;

import com.my.jasuil.data.entities.UserInfo;
import com.my.jasuil.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = {"/userinfo"})
public class UserInfoController {
    @Autowired
    UserInfoService userInfoService;

    @PostMapping("/new")
    public ResponseEntity save(@RequestBody UserInfo userInfo) {
        userInfoService.save(userInfo);
        return ResponseEntity.accepted().build();
    }

    @GetMapping("/info/id/{id}")
    public UserInfo findById(@PathVariable(name="id") Integer id) {
        return userInfoService.findById(id);
    }

    @GetMapping("/info/email/{email}")
    public UserInfo findById(@PathVariable(name="email") String email) {
        return userInfoService.findByEmail(email);
    }

    @DeleteMapping("/delete/email/{email}")
    public ResponseEntity deleteByEmail(@PathVariable(name="email") String email) {
        userInfoService.deleteByEmail(email);
        return ResponseEntity.accepted().build();
    }
}
