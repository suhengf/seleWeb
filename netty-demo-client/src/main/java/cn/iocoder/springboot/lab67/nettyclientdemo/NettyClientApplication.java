package cn.iocoder.springboot.lab67.nettyclientdemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class NettyClientApplication {

    public static void main(String[] args) {
        SpringApplication.run(NettyClientApplication.class, args);
    }

}
