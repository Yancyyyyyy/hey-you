package com.VastSky.boot.controller;

import com.VastSky.boot.bean.Appointment;
import com.VastSky.boot.bean.User;
import com.VastSky.boot.service.AppointmentService;
import com.mysql.cj.log.Log;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Controller
public class AppointmentController {
    @Autowired
    AppointmentService appointmentservice;
    @GetMapping("/discuss.html")
    public String discussPage(HttpSession session, Model model){
        // 拦截器
        Object loginUser = session.getAttribute("loginUser");
        if (loginUser != null) {
            User LoginUserInSession = (User) session.getAttribute("loginUser");
            User user_infor = appointmentservice.getUserByIdFromUsers(LoginUserInSession.getId());
            List<Appointment> list = appointmentservice.getAllRoom();
            model.addAttribute("rooms", list);
            model.addAttribute("user_infor", user_infor);
            return "discuss";
        } else {
            //返回登录页面
            model.addAttribute("msg", "Please log in again");
            return "login"; //这是若刷新页面也只是刷新Get请求，并不会重复提交表单了！&&如果没登陆也会直接跳转回login页面
        }
    }

    //select place
    @PostMapping("/discuss")
    public String postDiscussPage(HttpSession session, Appointment appointment, Model model){
        List<Appointment> list = appointmentservice.getAllRoom();
            String place = appointment.getPlace();
            List<Appointment> result = new ArrayList<>();
            if(place.equals("cb")){
                for (int i = 0; i < list.size(); i++) {if(list.get(i).getPlace().equals("cb")) result.add(list.get(i));}
            }
            else if(place.equals("bs")){
                for (int i = 0; i < list.size(); i++) {if(list.get(i).getPlace().equals("bs")) result.add(list.get(i));}
            }
            else if(place.equals("fb")){
                for (int i = 0; i < list.size(); i++) {if(list.get(i).getPlace().equals("fb")) result.add(list.get(i));}
            }
            else if(place.equals("all")){
                for (int i = 0; i < list.size(); i++) {result.add(list.get(i));}
            }
            else {
                for (int i = 0; i < list.size(); i++) {
                    if(!(list.get(i).getPlace().equals("fb") || list.get(i).getPlace().equals("bs") || list.get(i).getPlace().equals("cb"))){
                        result.add(list.get(i));
                    }
                }
            }
            model.addAttribute("rooms", result);
        User LoginUserInSession = (User) session.getAttribute("loginUser");
        User user_infor = appointmentservice.getUserByIdFromUsers(LoginUserInSession.getId());
        model.addAttribute("user_infor", user_infor);
        return "discuss";
    }
    @GetMapping("/addNewappointment.html")
    public String addNewappointmentPage(HttpSession session, Model model){
        // 拦截器
        Object loginUser = session.getAttribute("loginUser");
        if (loginUser != null) {
            User LoginUserInSession = (User) session.getAttribute("loginUser");
            User user_infor = appointmentservice.getUserByIdFromUsers(LoginUserInSession.getId());
            model.addAttribute("user_infor", user_infor);
            return "addNewappointment";
        } else {
            //返回登录页面
            model.addAttribute("msg", "Please log in again");
            return "login"; //这是若刷新页面也只是刷新Get请求，并不会重复提交表单了！&&如果没登陆也会直接跳转回login页面
        }

    }

    @PostMapping(value = { "/addNewappointment"})
    public String newAppointment(Appointment appointment, HttpSession session, Model model){
        User LoginUserInSession = (User) session.getAttribute("loginUser");
        appointment.setCreatorID(LoginUserInSession.getId());
        appointmentservice.addAppointment(appointment);
        List<Appointment> list = appointmentservice.getAllRoom();
        User user_infor = appointmentservice.getUserByIdFromUsers(LoginUserInSession.getId());
        model.addAttribute("rooms", list);
        model.addAttribute("user_infor", user_infor);
        return "discuss";
    }

    @GetMapping ("/myCreation.html")
    public String myReservationPage(HttpSession session, Model model){
        Object loginUser = session.getAttribute("loginUser");
        if (loginUser != null) {
            User LoginUserInSession = (User) session.getAttribute("loginUser");
            User user_infor = appointmentservice.getUserByIdFromUsers(LoginUserInSession.getId());
            List<Appointment> list = appointmentservice.getSelfRoom(LoginUserInSession.getId());
            model.addAttribute("rooms", list);
            model.addAttribute("user_infor", user_infor);
            return "myCreation";
        } else {
            //返回登录页面
            model.addAttribute("msg", "Please log in again");
            return "login"; //这是若刷新页面也只是刷新Get请求，并不会重复提交表单了！&&如果没登陆也会直接跳转回login页面
        }
    }

    @GetMapping("/detail")
    public String mapDetail(){
        return "login";
    }
    @PostMapping("/detail")
    public String postDetail(HttpSession session, Appointment appointment, Model model){
        Object loginUser = session.getAttribute("loginUser");
        if (loginUser != null) {
            if(appointment.getReservation() == -1){
                User LoginUserInSession = (User) session.getAttribute("loginUser");
                int room_num = appointment.getApNo();
                int creatorID = appointmentservice.getIDByapNo(room_num).getCreatorID(); //拿到房间创建者的ID
                User LoginUserInfor = appointmentservice.getUserByIdFromUsers(LoginUserInSession.getId()); //登陆的用户
                if(creatorID == LoginUserInfor.getId()){//登录用户就是房主
                    appointmentservice.deleteRoom(room_num);
                    User user_infor = appointmentservice.getUserByIdFromUsers(LoginUserInSession.getId());
                    List<Appointment> list = appointmentservice.getAllRoom();
                    model.addAttribute("rooms", list);
                    model.addAttribute("user_infor", user_infor);
                    return "discuss";
                }else{
                    session.setAttribute("login_infor", LoginUserInfor);
                    User user_infor = appointmentservice.getUserByIdFromUsers(creatorID); //房主
                    session.setAttribute("user_infor", user_infor);
                    String email = user_infor.getEmail();
                    session.setAttribute("creatorEmail", email);
                    if(user_infor.getNickname() == null){user_infor.setNickname(user_infor.getName());}
                    if(user_infor.getGender() == null){user_infor.setGender("Unknown Gender");}
                    if(user_infor.getEmail() == null){user_infor.setEmail("Unknown Email");}
                    if(user_infor.getPhone() == null){user_infor.setPhone("Unknown Phone");}
                    List<Appointment> list = appointmentservice.getAllRoom();
                    model.addAttribute("rooms", list);
                    model.addAttribute("user_infor", user_infor);
                    model.addAttribute("login_infor", LoginUserInfor);
                    model.addAttribute("apNo", room_num);
                    return "detail";
                }

            }else {//不是删除房间指令
                User LoginUserInSession = (User) session.getAttribute("loginUser");
                int room_num = appointment.getApNo();
                int creatorID = appointmentservice.getIDByapNo(room_num).getCreatorID(); //拿到房间创建者的ID
                User LoginUserInfor = appointmentservice.getUserByIdFromUsers(LoginUserInSession.getId()); //登陆的用户
                session.setAttribute("login_infor", LoginUserInfor);
                User user_infor = appointmentservice.getUserByIdFromUsers(creatorID); //房主
                session.setAttribute("user_infor", user_infor);
                String email = user_infor.getEmail();
                session.setAttribute("creatorEmail", email);
                if(user_infor.getNickname() == null){user_infor.setNickname(user_infor.getName());}
                if(user_infor.getGender() == null){user_infor.setGender("Unknown Gender");}
                if(user_infor.getEmail() == null){user_infor.setEmail("Unknown Email");}
                if(user_infor.getPhone() == null){user_infor.setPhone("Unknown Phone");}
                List<Appointment> list = appointmentservice.getAllRoom();
                model.addAttribute("rooms", list);
                model.addAttribute("user_infor", user_infor);
                model.addAttribute("login_infor", LoginUserInfor);
                model.addAttribute("apNo", room_num);
                return "detail";
            }

            }else{
            //返回登录页面
            model.addAttribute("msg", "Please log in again");
            return "login"; //这是若刷新页面也只是刷新Get请求，并不会重复提交表单了！&&如果没登陆也会直接跳转回login页面
        }
    }

}
