package com.cmj.park.test;

import org.junit.jupiter.api.Test;

import java.util.Random;
import java.util.UUID;

/*
    这是注释
    
*/
public class testUUId {
    @Test
    public void test1(){
        int i = new Random().nextInt(9);
        System.out.println(UUID.randomUUID().toString().replaceAll("-","").substring(i, i+8));
    }
}
