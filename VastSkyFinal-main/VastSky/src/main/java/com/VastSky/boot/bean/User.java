package com.VastSky.boot.bean;

import lombok.Data;

@Data
public class User {
    private int id;
    private String name;
    private String pwd;
    private String gender;
    private String email;
    private String phone;
    private String avatar;
    private String nickname;
}
