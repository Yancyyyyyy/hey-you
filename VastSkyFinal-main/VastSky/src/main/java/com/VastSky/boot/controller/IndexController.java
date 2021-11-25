package com.VastSky.boot.controller;

import com.VastSky.boot.bean.ChangePwdUser;
import com.VastSky.boot.bean.User;
import com.VastSky.boot.bean.plan;
import com.VastSky.boot.bean.sign_up_User;
import com.VastSky.boot.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Controller
public class IndexController {
    @Autowired
    UserService userservice;
    //首页跳转
    @GetMapping(value = {"/", "/login","/login.html"}) // 设置匹配“/”或者“/login”都等于去到登录页面
    public String loginPage(HttpSession httpSession){
        //登录页面
        Object loginUser = httpSession.getAttribute("loginUser");
        if(loginUser != null){
            httpSession.removeAttribute("loginUser");
            return "login";
        }
        return "login";
    }

    //登录
    @PostMapping("/login") //因为要登陆所以要发Post请求，这里的意思是只有发Post请求的/login才是真正的登录
    public String index(User user, HttpSession session, Model model){
            User signed_user = userservice.getUserByIdFromUsers(user.getId());//从user表中取出用户信息
            User admin = userservice.getUserByIdFromAdmins(user.getId());//从admins表中取出用户信息
            if(signed_user != null && admin == null){//是已经注册的id且不是管理员
                if(user.getPwd().equals(signed_user.getPwd())){//密码正确
                    //把登录成功的用户保存起来
                    session.setAttribute("loginUser", user);
                    //登录成功重定向到home.html; 防止表单重复提交
                    //这里是普通用户跳转到home
                    return "redirect:/home.html";//我们提交表单进入主页面后，为了避免刷新页面会重复post提交表单，所以我们将它定向到下面的方法处理
                } else{//密码错误
                    model.addAttribute("msg","Account or password is wrong");
                    return "login";
                }
            }
            else if(signed_user != null && admin != null){//是已经注册的id并且是管理员
                if(user.getPwd().equals(signed_user.getPwd())){//是管理员
                    //把登录成功的用户保存起来
                    session.setAttribute("loginUser", user);
                    //登录成功重定向到home.html; 防止表单重复提交
                    //这里是管理员跳转到success
                    return "redirect:/home.html";//我们提交表单进入主页面后，为了避免刷新页面会重复post提交表单，所以我们将它定向到下面的方法处理
                } else{//密码错误
                    model.addAttribute("msg","Account or password is wrong");
                    return "login";
                }
            }
            else{//用户没有注册
                model.addAttribute("msg","ID is not registered");
                return "login";
            }
    }

    //登录成功，跳转到home page
    @GetMapping("/home.html")
    public String homePage(HttpSession session, Model model){
        //判断是否登录 拦截器 过滤器
        Object loginUser = session.getAttribute("loginUser");

        if(loginUser != null){
            User LoginUserInSession = (User) session.getAttribute("loginUser");
            User user_infor = userservice.getUserByIdFromUsers(LoginUserInSession.getId());
            //更改主页右上角正在学习的人数
                List<Integer> list = userservice.studying();
                Integer number_of_studying = 0;
                for (int i = 0; i < list.size(); i++) {
                    number_of_studying += list.get(i);
                }
                model.addAttribute("studing", number_of_studying);

            //统计不同places自习的人数
                List<String> list_place = userservice.studyPlace();
                Integer cb = 0;
                Integer bs = 0;
                Integer fb = 0;
                Integer other_place = 0;
                for (int i = 0; i < list_place.size(); i++) {
                    if(list_place.get(i).equals("cb") || list_place.get(i).equals("CB")){
                        cb += list.get(i);
                    }
                    else if(list_place.get(i).equals("bs") || list_place.get(i).equals("BS")){
                        bs += list.get(i);
                    }
                    else if(list_place.get(i).equals("fb") || list_place.get(i).equals("FB")){
                        fb += list.get(i);
                    }
                    else {
                        other_place += list.get(i);
                    }
                }
                model.addAttribute("cb", cb);
                model.addAttribute("bs", bs);
                model.addAttribute("fb", fb);
                model.addAttribute("other_place", other_place);

                //获取计划表
                List<plan> study_plan = userservice.studyPlan();
                model.addAttribute("plan", study_plan);
                model.addAttribute("user_infor", user_infor);


            //作为管理员刷新页面会跳转到普通用户界面（不要刷新页面）
            return "home";
        }else {
            //返回登录页面
            model.addAttribute("msg", "Please log in again");
            return "login"; //这是若刷新页面也只是刷新Get请求，并不会重复提交表单了！
        }
    }
    //增加新todolist后跳转
    @PostMapping("home.html")
    public String PostHomePage(plan plan, HttpSession session, Model model){
        userservice.addPlan(plan);
        //判断是否登录 拦截器 过滤器
        Object loginUser = session.getAttribute("loginUser");
        if(loginUser != null){

            //更改主页右上角正在学习的人数
            List<Integer> list = userservice.studying();
            Integer number_of_studying = 0;
            for (int i = 0; i < list.size(); i++) {
                number_of_studying += list.get(i);
            }
            model.addAttribute("studing", number_of_studying);

            //统计不同places自习的人数
            List<String> list_place = userservice.studyPlace();
            Integer cb = 0;
            Integer bs = 0;
            Integer fb = 0;
            Integer other_place = 0;
            for (int i = 0; i < list_place.size(); i++) {
                if(list_place.get(i).equals("cb") || list_place.get(i).equals("CB")){
                    cb += list.get(i);
                }
                else if(list_place.get(i).equals("bs") || list_place.get(i).equals("BS")){
                    bs += list.get(i);
                }
                else if(list_place.get(i).equals("fb") || list_place.get(i).equals("FB")){
                    fb += list.get(i);
                }
                else {
                    other_place += list.get(i);
                }
            }
            model.addAttribute("cb", cb);
            model.addAttribute("bs", bs);
            model.addAttribute("fb", fb);
            model.addAttribute("other_place", other_place);

            //获取计划表
            List<plan> study_plan = userservice.studyPlan();
            model.addAttribute("plan", study_plan);


            //作为管理员刷新页面会跳转到普通用户界面（不要刷新页面）
            return "home";
        }else {
            //返回登录页面
            model.addAttribute("msg", "Please log in again");
            return "login"; //这是若刷新页面也只是刷新Get请求，并不会重复提交表单了！
        }
    }

    //跳转到注册页面
    @GetMapping("/registration.html")
    public String registrationPage() {
        return "registration";
    }

    //注册过后信息跳转
    @PostMapping("/registration")
    public String registrationPost(sign_up_User user, Model model){
        System.out.println(user);
        System.out.println(user.getId());
        User signed_user = userservice.getUserByIdFromUsers(user.getId());//从user表中取出用户信息
        User legal_student = userservice.getUserByIdFromLegalStudentSet(user.getId());//查看是否是本校学生
        System.out.println(signed_user);
        System.out.println(legal_student);
        if(signed_user == null){//未注册
            if(legal_student != null){//是本校学生
                String fullName = user.getFirst_name() +"." + user.getLast_name();
                int id = user.getId();
                String pwd1 = user.getPwd1();
                String pwd2 = user.getPwd2();
                if(!pwd1.equals(pwd2)){
                    model.addAttribute("msg", "Password is different");
                    return "registration";
                }else{
                    User signing_user = new User();
                    signing_user.setId(id);
                    signing_user.setName(fullName);
                    signing_user.setPwd(pwd1);
                    if(legal_student.getName().equals(fullName)){//名字一致
                        int flag = userservice.addUser(signing_user);
                        if(flag == 1){
                            model.addAttribute("msg", "Registration successful!");
                            return "registration";
                        }else{
                            model.addAttribute("msg", "Registration failed");
                            return "registration";
                        }
                    }else {//名字不一致
                        model.addAttribute("msg", "Illegal student name");
                        return "registration";
                    }
                }
            }else{//不是本校学生
                model.addAttribute("msg", "Sorry, Only for XJTLU student");
                return "registration";
            }
        }else{//已经注册
            model.addAttribute("msg", "This ID has been registered");
            return "registration";
        }
    }


    //忘记密码
    @GetMapping("/forgetPassword")
    public String authenticationPage(){
    //点了忘记密码跳转到changePassword1页面，用于验证身份信息
        return "forgetPassword";
    }

    //修改密码后的跳转
    @PostMapping("/forgetPassword")
    public String changePassword2Now(ChangePwdUser user, HttpSession session, Model model){
        Map<String, Object> map = new HashMap<>();
        User verify_user = userservice.getUserByIdFromUsers(user.getId());//从user表中取出用户信息
        String pwd1 = user.getPwd1(); //User输入的第一次密码
        String pwd2 = user.getPwd2();//User输入的第二次密码
        if(verify_user != null){ //说明输入的ID是存在于users表中的
            if(user.getName().equals(verify_user.getName())){ //说明表单提交的名字等于数据库中该ID的名字，则认证成功可以修改密码
                if(pwd1.equals(pwd2)){ //两次输入的密码一致
                    map.put("id",verify_user.getId());
                    map.put("pwd",pwd2);
                    userservice.modifyPassword(map);
                    model.addAttribute("msg", "Modified successfully");
                    return "forgetPassword";
                }
                else{//两次输入的密码不一致
                    model.addAttribute("msg", "The two passwords are inconsistent");
                    return "forgetPassword";
                }
            }
            else{//说明提交的名字与数据库中该ID的名字不符，验证失败
                model.addAttribute("msg", "Username is wrong!");
                return "forgetPassword";
            }
        }
        else{//说明输入的ID不存在于Users表中
            model.addAttribute("msg", "ID is not registered");
            return "forgetPassword";
        }
    }

}
