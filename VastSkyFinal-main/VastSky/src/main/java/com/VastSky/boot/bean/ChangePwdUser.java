package com.VastSky.boot.bean;

import lombok.Data;

@Data
public class ChangePwdUser {
    private int id;
    private String name;
    private String pwd1;
    private String pwd2;
}
