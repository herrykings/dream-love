package cn.smdq.ch.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * @author shuimodanqing
 * create at:  2019/6/18  11:35 PM
 * @description
 */
@SpringBootApplication
@EnableEurekaServer
@ConfigurationProperties
public class DreamServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(DreamServerApplication.class, args);
    }
}
