package com.VastSky.boot;

import com.VastSky.boot.bean.Appointment;
import com.VastSky.boot.bean.ChangePwdUser;
import com.VastSky.boot.bean.User;
import com.VastSky.boot.bean.sign_up_User;
import com.VastSky.boot.mapper.UserMapper;
import com.VastSky.boot.service.AppointmentService;
import com.VastSky.boot.service.UserService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


@DisplayName("单元测试")
@SpringBootTest
public class Test {
    @Autowired
    UserService userservice;
    @Autowired
    UserMapper usermapper;
    @BeforeAll
    static void beforetest(){
        System.out.println("准备好了吗？所有测试马上就要开始啦！");
    }
    @AfterAll
    static void aftereach(){
        System.out.println("所有都测试成功啦！！good job！");
    }

    public String index(User user){
        User signed_user = userservice.getUserByIdFromUsers(user.getId());//从user表中取出用户信息
        User admin = userservice.getUserByIdFromAdmins(user.getId());
        if(signed_user != null && admin == null){//是已经注册的id且不是管理员
            if(user.getPwd().equals(signed_user.getPwd())){//密码正确
                return "redirect:/home.html, pass";//我们提交表单进入主页面后，为了避免刷新页面会重复post提交表单，所以我们将它定向到下面的方法处理
            } else{//密码错误
                return "login, fail";
            }
        }
        else if(signed_user != null && admin != null){//是已经注册的id并且是管理员
            if(user.getPwd().equals(signed_user.getPwd())){//是管理员
                return "redirect:/home.html, pass";//我们提交表单进入主页面后，为了避免刷新页面会重复post提交表单，所以我们将它定向到下面的方法处理
            } else{//密码错误
                return "login, fail, Wrong id or pwd";
            }
        }
        else{//用户没有注册
            return "login, fail, ID is not registered";
        }
    }





    @DisplayName("Test valid user index ")
    @org.junit.jupiter.api.Test
    public void testcase1(){
        User user=new User();
        user.setId(1823811);
        user.setPwd("1823811");
        String testcase = index(user);
        assertEquals("redirect:/home.html, pass",testcase);
    }

    @DisplayName("Test response for wrong user password entered")
    @org.junit.jupiter.api.Test
    public void testcase2(){
        User user=new User();
        user.setId(1823482);
        user.setPwd("wrongpwd");
        String testcase = index(user);
        assertEquals("login, fail, Wrong id or pwd",testcase);
    }

    @DisplayName("Test invalid user account")
    @org.junit.jupiter.api.Test
    public void testcase3(){
        User user=new User();
        user.setId(1234567);
        user.setPwd("6666666");
        String testcase = index(user);
        assertEquals("login, fail, ID is not registered",testcase);
    }



    public String registrationPost(sign_up_User user){
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
                    return "registration, Password is different";
                }else{
                    User signing_user = new User();
                    signing_user.setId(id);
                    signing_user.setName(fullName);
                    signing_user.setPwd(pwd1);
                    if(legal_student.getName().equals(fullName)){//名字一致
                        int flag = userservice.addUser(signing_user);
                        if(flag == 1){
                            return "registration, Registration successful!";
                        }else{
                            return "registration, Registration failed";
                        }
                    }else {//名字不一致
                        return "registration, Illegal student name";
                    }
                }
            }else{//不是本校学生
                return "registration, Sorry, Only for XJTLU student";
            }
        }else{//已经注册
            return "registration, This ID has been registered";
        }
    }

    @DisplayName("test register function")
    @org.junit.jupiter.api.Test
    public void testcase4(){
        sign_up_User user=new sign_up_User();
        user.setFirst_name("Tao");
        user.setLast_name("Ruan");
        user.setId(1823486);
        user.setPwd1("1823486");
        user.setPwd2("1823486");
        String testcase = registrationPost(user);
        assertEquals("registration, Registration successful!",testcase);
    }
    public String authenticationPage(){
        return "forgetPassword";
    }

    @DisplayName("Test consistency of password entered twice")
    @org.junit.jupiter.api.Test
    public void testcase5(){
        sign_up_User user=new sign_up_User();
        user.setFirst_name("Ling");
        user.setLast_name("Li");
        user.setId(1823000);
        user.setPwd1("1823001");
        user.setPwd2("1823000");
        String testcase = registrationPost(user);
        assertEquals("registration, Password is different",testcase);
    }
    @DisplayName("Test illegal user name ")
    @org.junit.jupiter.api.Test
    public void testcase6(){
        sign_up_User user=new sign_up_User();
        user.setFirst_name("Haha");
        user.setLast_name("Li");
        user.setId(1823000);
        user.setPwd1("1823000");
        user.setPwd2("1823000");
        String testcase = registrationPost(user);
        assertEquals("registration, Illegal student name",testcase);
    }

    @DisplayName("Test illegal student id ")
    @org.junit.jupiter.api.Test
    public void testcase7(){
        sign_up_User user=new sign_up_User();
        user.setFirst_name("Tao");
        user.setLast_name("Ruan");
        user.setId(2299999);
        user.setPwd1("1823486");
        user.setPwd2("1823486");
        String testcase = registrationPost(user);
        assertEquals("registration, Sorry, Only for XJTLU student",testcase);
    }

    @DisplayName("Test duplicate registration ")
    @org.junit.jupiter.api.Test
    public void testcase8(){
        sign_up_User user=new sign_up_User();
        user.setFirst_name("Keyao");
        user.setLast_name("Huang");
        user.setId(1823482);
        user.setPwd1("1823482");
        user.setPwd2("1823482");
        String testcase = registrationPost(user);
        assertEquals("registration, This ID has been registered",testcase);
    }


    public String changePassword2Now(ChangePwdUser user ){
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
                    return "forgetPassword, Modified successfully";
                }
                else{//两次输入的密码不一致

                    return "forgetPassword, The two passwords are inconsistent";
                }
            }
            else{//说明提交的名字与数据库中该ID的名字不符，验证失败

                return "forgetPassword, Username is wrong!";
            }
        }
        else{//说明输入的ID不存在于Users表中

            return "forgetPassword, ID is not registered";
        }
    }
    @DisplayName("Test password reset function")
    @org.junit.jupiter.api.Test
    public void testcase9(){
        ChangePwdUser user=new ChangePwdUser();
        user.setName("Keyao.Huang");
        user.setId(1823482);
        user.setPwd1("998765");
        user.setPwd2("998765");
        String testcase2 = changePassword2Now(user);
        String testcase1= authenticationPage();
        assertEquals("forgetPassword",testcase1);
        assertEquals("forgetPassword, Modified successfully",testcase2);
    }

    @DisplayName("Test information entered consistancy for password reset function")
    @org.junit.jupiter.api.Test
    public void testcase10(){
        ChangePwdUser user=new ChangePwdUser();
        user.setName("Keyao.Huang");
        user.setId(1823482);
        user.setPwd1("998765");
        user.setPwd2("998766");
        String testcase2 = changePassword2Now(user);
        String testcase1= authenticationPage();
        assertEquals("forgetPassword",testcase1);
        assertEquals("forgetPassword, The two passwords are inconsistent",testcase2);
    }

    @DisplayName("Test user name in password reset function")
    @org.junit.jupiter.api.Test
    public void testcase11(){
        ChangePwdUser user=new ChangePwdUser();
        user.setName("Keyao.Chen");
        user.setId(1823482);
        user.setPwd1("998765");
        user.setPwd2("998765");
        String testcase2 = changePassword2Now(user);
        String testcase1= authenticationPage();
        assertEquals("forgetPassword",testcase1);
        assertEquals("forgetPassword, Username is wrong!",testcase2);
    }

    @DisplayName("Test user id in password reset function")
    @org.junit.jupiter.api.Test
    public void testcase12(){
        ChangePwdUser user=new ChangePwdUser();
        user.setName("Keyao.Huang");
        user.setId(1823481);
        user.setPwd1("998765");
        user.setPwd2("998765");
        String testcase2 = changePassword2Now(user);
        String testcase1= authenticationPage();
        assertEquals("forgetPassword",testcase1);
        assertEquals("forgetPassword, ID is not registered",testcase2);
    }


    public String changeInfo(User user, MultipartFile headerImage,String PostedNickname, String PostedGender,String PostedEmail, String PostedPhone) throws IOException {
        Map<String, Object> map = new HashMap<>();
        User LoginUserInSession = user;
        User LoginUser = userservice.getUserByIdFromUsers(LoginUserInSession.getId());
//        String PostedNickname = user.getNickname();
//        String PostedGender = user.getGender();
//        String PostedEmail = user.getEmail();
//        String PostedPhone = user.getPhone();
//        if(user.getNickname().equals("6GQikc7svV")){ //注销账户
//            //userservice.deleteUser(LoginUserInSession.getId());
//            return "login";
//        }
        if ((!PostedNickname.equals(LoginUser.getName())) || !PostedGender.equals(LoginUser.getGender()) || !PostedEmail.equals(LoginUser.getEmail()) || !PostedPhone.equals(LoginUser.getPhone())) {
            if(!PostedNickname.equals("")){map.put("nickname", PostedNickname);}else {map.put("nickname", LoginUser.getNickname());}
            if(!PostedGender.equals("null")){map.put("gender", PostedGender);}else {map.put("gender", LoginUser.getGender());}
            if(!PostedPhone.equals("")){map.put("phone", PostedPhone);}else {map.put("phone", LoginUser.getPhone());}
            if(!PostedEmail.equals("")){map.put("email", PostedEmail);}else {map.put("email", LoginUser.getEmail());}


            if(false /*!headerImage.isEmpty()*/){
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
            //model.addAttribute("user", LoginUser);
            return "profile, submit success";
        } else {
            return "profile, Submit failed";
        }
    }





    @DisplayName("test profile modification process")
    @org.junit.jupiter.api.Test
    public void testcase13() throws IOException {
        User user=new User();
        user.setName("Keyao.Huang");
        user.setId(1823482);
        user.setPwd("1823482");
        MultipartFile headerImage= null;
        String PostedNickname="badguy2021";
        String PostedGender="male";
        String PostedEmail="badguy2021@gmail.com";
        String PostedPhone="18089999999";
        String testcase= changeInfo(user,headerImage,PostedNickname,PostedGender,PostedEmail,PostedPhone);
        assertEquals("profile, submit success",testcase);
    }

    @Autowired
    AppointmentService appointmentservice;

    public String newAppointment(Appointment appointment, User user){
        User LoginUserInSession = user;
        appointment.setCreatorID(LoginUserInSession.getId());
        appointmentservice.addAppointment(appointment);
        List<Appointment> list = appointmentservice.getAllRoom();
        User user_infor = appointmentservice.getUserByIdFromUsers(LoginUserInSession.getId());
//        model.addAttribute("rooms", list);
//        model.addAttribute("user_infor", user_infor);
        return "Sccess!";
    }

    @DisplayName("test add appointment function")
    @org.junit.jupiter.api.Test
    public void testcase14()  {
        User user=new User();
        user.setName("Keyao.Huang");
        user.setId(1823482);
        user.setPwd("1823482");
        Appointment appointment=new Appointment();
        appointment.setAptime("12:00-12:30");
        appointment.setPlace("bs/3/392");
        appointment.setReservation(2);
        String testcase= newAppointment(appointment,user);
        assertEquals("Sccess!",testcase);
    }




}

