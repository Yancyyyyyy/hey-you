package com.VastSky.boot.controller;

import com.VastSky.boot.bean.User;
import com.VastSky.boot.service.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;

@Controller
public class MailController {
    @Autowired
    MailService mailService;

    @PostMapping("/SendMail")
    public String SendMail(HttpSession session, Model model){
        System.out.println("this is in sendMail method");
        String creatorEmail =(String) session.getAttribute("creatorEmail"); //进入房间的时候拿到了房主的Email
        User owner =(User) session.getAttribute("user_infor"); //房主
        User user =(User) session.getAttribute("login_infor"); //登陆的用户

        String name = user.getNickname(); //下列四行是登陆的用户的名字，性别，电话，邮件，这四个信息将用来当作邮件的内容
        String gender = user.getGender();
        String phone = user.getPhone();
        String email = user.getEmail();
        model.addAttribute("user_infor", owner);
        model.addAttribute("login_infor", user);

        if(creatorEmail == null){
            System.out.println("Email is null");
        }
        else {
            System.out.println("Creator email is: " + creatorEmail);
        }

        String content = "Name: " + name + "\nGender: " + gender + "\nPhone: " + phone + "\nemail: " + email;

        boolean sendFlag = mailService.sendSimpleMail(creatorEmail, content);
        if(sendFlag == true){
            model.addAttribute("msg", "Send successfully");
            return "detail";
        }
        else{
            model.addAttribute("msg", "send failure");
            return "detail";
        }
    }

}
