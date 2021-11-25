package com.VastSky.boot.service;
import com.VastSky.boot.bean.User;
import com.VastSky.boot.bean.plan;
import com.VastSky.boot.bean.sign_up_User;
import com.VastSky.boot.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class UserService {

    @Autowired
    UserMapper usermapper;


    public User getUserByIdFromUsers(int id){return usermapper.getUserByIdFromUsers(id);}
    public int deleteUser(int id){return usermapper.deleteUser(id);}
    public User getUserByIdFromAdmins(int id){return usermapper.getUserByIdFromAdmins(id);}
    public User getUserByIdFromLegalStudentSet(int id){return usermapper.getUserByIdFromLegalStudentSet(id);}
    public int addUser(User user){
        usermapper.addUser(user);
        User new_user = usermapper.getUserByIdFromUsers(user.getId());
        if (new_user != null){
            return 1;
        }else {
            return 0;
        }
    }
    public int modifyPassword(Map<String, Object> map){return usermapper.modifyPassword(map);}
    public List<Integer> studying(){return usermapper.studying();};
    public List<String> studyPlace(){return usermapper.studyPlace();}
    public List<plan> studyPlan(){return usermapper.studyPlan();}
    public void addPlan(plan plan){usermapper.addPlan(plan);}
    public int updateUserInfo(Map<String, Object> map){
        return usermapper.updateUserInfo(map);
    }
    public int updateUserHeadImage(Map<String, Object> map){ return usermapper.updateUserHeadImage(map);}

}
