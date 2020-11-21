package com.my.jasuil;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class TestDTO implements Serializable {

    String body;
    List<Integer> idList;
}
