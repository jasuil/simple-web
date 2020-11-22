package com.my.jasuil.controller;

import com.my.jasuil.TestDTO;
import com.my.jasuil.service.MyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

@RestController
public class MyController {
    @Autowired
    MyService myService;

    @GetMapping("/hi")
    public ResponseEntity hi(@RequestParam(name="idList") List<Integer> idList){

        TestDTO dto = new TestDTO();
        dto.setBody(Arrays.toString(idList.toArray()));
        dto.setIdList(myService.hi(idList));
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(dto);
    }

    @GetMapping("/today")
    public ResponseEntity getToday() {
        return ResponseEntity.ok(new Date());
    }

}

