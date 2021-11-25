package com.VastSky.boot.Myconfig;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class PicConfig implements WebMvcConfigurer {


    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry){
        System.out.println("配置文件生效");
        registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
        String path = System.getProperty("user.dir") + "\\src\\main\\resources\\static\\HeaderImage\\";
        path = path.replaceAll("\\\\", "/");
        registry.addResourceHandler("/HeaderImage/**").addResourceLocations("file:"+ path);
        System.out.println(path);
    }



}
