package com.VastSky.boot.mapper;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MailMapper {
    public boolean sendSimpleMail(String mail, String Content);
}
