package com.VastSky.boot.Impl;

import com.VastSky.boot.mapper.MailMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class MailMapperImpl implements MailMapper {
    @Autowired
    private JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String from;

    @Override
    public boolean sendSimpleMail(String email, String content) {
        //创建简单邮件消息
        SimpleMailMessage message = new SimpleMailMessage();
        //谁发的
        message.setFrom(from);
        //谁要接收
        message.setTo(email);
        //邮件标题
        message.setSubject("New appointment request");
        //邮件内容
//        int content =(int)(Math.random()*(9999-1000+1)+1000);
//        String realContent = Integer.toString(content);
//        message.setText(realContent);
        message.setText(content);
        try{
            mailSender.send(message);
            System.out.println("发送邮件成功");
            return true;
        }catch (MailException e){
            e.printStackTrace();
            System.out.println("发送邮件失败");
            return false;
        }

    }
}
