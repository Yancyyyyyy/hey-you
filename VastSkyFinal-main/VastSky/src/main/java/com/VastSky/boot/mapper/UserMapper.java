package com.VastSky.boot.mapper;

import com.VastSky.boot.bean.Appointment;
import com.VastSky.boot.bean.User;
import com.VastSky.boot.bean.plan;
import com.VastSky.boot.bean.sign_up_User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

//这是一个接口
@Mapper
public interface UserMapper {


    //在当前的用户的表insert一个用户（用户注册）
    public void addUser(User user);

    //删除当前的用户的表一个用户(用户注销)
    int deleteUser(int id);

    //在注册表中查寻用户（登录）
    public User getUserByIdFromUsers(int id);

    //在校内同学表中查询是否符合注册资格（注册）
    public User getUserByIdFromLegalStudentSet(int id);

    //查看该登录用户是不是管理员
    public User getUserByIdFromAdmins(int id);

    //修改id密码
    public int modifyPassword(Map<String, Object> map);

    //获取正在自习的人数
    public List<Integer> studying();

    //统计不同地方自习的人数
    public List<String> studyPlace();

    //获取计划表
    public List<plan> studyPlan();

    //增加新计划
    public void addPlan(plan plan);

    //更新profile信息
    public int updateUserInfo(Map<String, Object> map);

    public int updateUserHeadImage(Map<String, Object> map);

}
