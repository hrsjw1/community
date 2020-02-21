package com.example.controller;

import com.example.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @RestController : 返回字符串 json 串
 * @Controller 返回页面
 * @ResponseBody    : 将返回的对象转化成json 串传页面
 */

@Controller
@RequestMapping("/testBoot")
public class UserController {
    @Autowired
    UserMapper userMapper ;
    @ResponseBody
    @RequestMapping("getUser/{id}")
    String GetUser (@PathVariable int id ){
        return userMapper.getUserById(id).toString();
    }

    @RequestMapping("welcome")
    String getWelcome(){
        return "html/welcome.html";
    }
}
