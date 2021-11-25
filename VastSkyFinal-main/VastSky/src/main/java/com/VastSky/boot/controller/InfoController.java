package com.VastSky.boot.controller;

import com.VastSky.boot.bean.User;
import com.VastSky.boot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Controller

public class InfoController {
    @Autowired
    UserService userservice;

    @GetMapping("/profile.html")
    public String ProfilePage(HttpSession session, Model model) {
        //判断是否登录 拦截器 过滤器
        Object loginUser = session.getAttribute("loginUser");
        if (loginUser != null) {//作为管理员刷新页面会跳转到普通用户界面（不要刷新页面）
            User LoginUserInSession = (User) session.getAttribute("loginUser");
            User LoginUser = userservice.getUserByIdFromUsers(LoginUserInSession.getId());
            model.addAttribute("user", LoginUser);
            return "profile"; //如果登陆了可以去到profile界面
        } else {
            //返回登录页面
            model.addAttribute("msg", "Please log in again");
            return "login"; //这是若刷新页面也只是刷新Get请求，并不会重复提交表单了！&&如果没登陆也会直接跳转回login页面
        }
    }

    @PostMapping("/profile.html")
    public String changeInfo(User user, HttpSession session, Model model, MultipartFile headerImage) throws IOException {
        Map<String, Object> map = new HashMap<>();
        User LoginUserInSession = (User) session.getAttribute("loginUser");
        User LoginUser = userservice.getUserByIdFromUsers(LoginUserInSession.getId());
        String PostedNickname = user.getNickname();
        String PostedGender = user.getGender();
        String PostedEmail = user.getEmail();
        String PostedPhone = user.getPhone();
        if(user.getNickname().equals("6GQikc7svV")){ //注销账户
            userservice.deleteUser(LoginUserInSession.getId());
            return "login";
        }
        if ((!PostedNickname.equals(LoginUser.getName())) || !PostedGender.equals(LoginUser.getGender()) || !PostedEmail.equals(LoginUser.getEmail()) || !PostedPhone.equals(LoginUser.getPhone())) {
            if(!PostedNickname.equals("")){map.put("nickname", PostedNickname);}else {map.put("nickname", LoginUser.getNickname());}
            if(!PostedGender.equals("null")){map.put("gender", PostedGender);}else {map.put("gender", LoginUser.getGender());}
            if(!PostedPhone.equals("")){map.put("phone", PostedPhone);}else {map.put("phone", LoginUser.getPhone());}
            if(!PostedEmail.equals("")){map.put("email", PostedEmail);}else {map.put("email", LoginUser.getEmail());}


            if(!headerImage.isEmpty()){
                String originalFilename = headerImage.getOriginalFilename(); //获得上传图片的名字
                String path = System.getProperty("user.dir") + "\\src\\main\\resources\\static\\HeaderImage\\";
                path = path.replaceAll("\\\\", "/");
                headerImage.transferTo(new File(path + originalFilename));
//                headerImage.transferTo(new File("D:\\CPT202CW\\VastSky\\target\\" + originalFilename));
                map.put("avatar", originalFilename); }
            else{map.put("avatar", LoginUser.getAvatar());}



            map.put("id", LoginUserInSession.getId());
            userservice.updateUserInfo(map);
            userservice.updateUserHeadImage(map);
            LoginUser = userservice.getUserByIdFromUsers(LoginUserInSession.getId());
            model.addAttribute("user", LoginUser);
            return "profile";
        } else {
            model.addAttribute("msg", "Submit failed");
            return "profile";
        }
    }
}

