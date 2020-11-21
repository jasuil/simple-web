package com.my.jasuil.data;

import org.jooq.DSLContext;
import org.jooq.impl.DSL;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Map;

@SpringBootTest
@DisplayName("jooq test")
public class PostJooqTest {
    @Autowired
    DSLContext context;

    @Test
    public void selectTest(){
        List<Map<String, Object>> map = context.select(DSL.value(1).as("one")).from(DSL.dual()).fetchMaps();
        Assertions.assertTrue(map.get(0).get("one").equals((Integer.valueOf(1))));
    }
}
