package com.example.demo;

import com.example.demo.bean.Person;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@SpringBootTest

class DemoApplicationTests {
    @Autowired
   Person person;

    @Test
   public  void contextLoads() {
        System.out.println("person===="+person);
//        System.out.println("age======="+person.getAge());
//        System.out.println("dog.name======="+person.getDog().getName()+" dog.age===="+person.getDog().getAge());
    }

}


