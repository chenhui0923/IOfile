package com.example.demo.Controller;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@Controller

//public class helloController {
//    @ResponseBody
//    @RequestMapping("/index")
//    public String sayhello(){
//
//        return "index.html";
//    }
//}

public class helloController {

    @RequestMapping("/hello")
    public String helloHtml(HashMap<String, Object> map) {
        map.put("hello", "欢迎进入HTML页面");
        return "/index";
    }
    @RequestMapping("/index")
    public String sayhello(){
        return "/index";
    }


}