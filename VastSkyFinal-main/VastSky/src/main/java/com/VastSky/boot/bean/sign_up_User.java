package com.VastSky.boot.bean;
import lombok.Data;

@Data
public class sign_up_User {
    private int id;
    private String first_name;
    private String last_name;
    private String pwd1;
    private String pwd2;
}
