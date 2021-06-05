package com.ruoyi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/**
 * 启动程序
 *
 * @author wyt
 */
@SpringBootApplication(exclude = { DataSourceAutoConfiguration.class })
public class RuoYiWxApplication {

    public static void main(String[] args) {
        SpringApplication.run(RuoYiWxApplication.class, args);
        System.out.println("系统启动成功");
    }
}
