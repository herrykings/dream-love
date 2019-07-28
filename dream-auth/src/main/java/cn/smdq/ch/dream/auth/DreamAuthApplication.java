package cn.smdq.ch.dream.auth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * @author shuimodanqing
 * create at:  2019/6/24  11:23 PM
 * @description
 */
@SpringBootApplication
@EnableEurekaClient
public class DreamAuthApplication {
    public static void main(String[] args) {
        SpringApplication.run(DreamAuthApplication.class, args);
    }
}
