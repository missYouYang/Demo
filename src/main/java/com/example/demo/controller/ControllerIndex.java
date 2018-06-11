package com.example.demo.controller;

import com.example.demo.model.User;
import com.example.demo.notService.ServiceNote;
import com.example.demo.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * Created by Lenovo on 2018/5/24.
 */
@Controller
@RequestMapping("user")
public class ControllerIndex {

    @Autowired
    private TestService testService;

    @RequestMapping(value = "{index}")
    @ServiceNote(desc = "登入界面",auth = ServiceNote.AUTH.PUBLIC)
    public String login(@PathVariable("index") String index) {

        return index;
    }
    @RequestMapping("/userLogin")
    @ServiceNote(desc = "用户登录",auth = ServiceNote.AUTH.PUBLIC)
    public String userLogin(ModelMap map, String userName, String password){

        User user = testService.getUserByUserNameAndPassword(userName,password);
        if(StringUtils.isEmpty(user)){

            map.put("message","你输入的用户或密码不正确");
            return "forward:login";
        }else {
            map.put("success","恭喜登入成功");
            return "success";
        }
    }
    @RequestMapping("/userRegister")
    @ServiceNote(desc = "用户注册",auth = ServiceNote.AUTH.PUBLIC)
    public String userRegister(HttpServletRequest request, HttpServletResponse response,User user){

       int a =  testService.addUser(user);

        if(a == 1){
            request.setAttribute("message","恭喜注册成功");
            return "success";
        }else {
            request.setAttribute("success","注册失败");
            return "login";
        }
    }
}
