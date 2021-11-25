package com.VastSky.boot.service;

import com.VastSky.boot.Impl.MailMapperImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MailService {
    @Autowired
    MailMapperImpl mailMapper;

    public boolean sendSimpleMail(String email, String Content){ return mailMapper.sendSimpleMail(email, Content);}

}
