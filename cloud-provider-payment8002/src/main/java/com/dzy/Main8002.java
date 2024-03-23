package com.dzy;



import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringBootVersion;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@MapperScan("com.dzy.mapper")
@EnableDiscoveryClient
@RefreshScope
public class Main8002 {

    public  static  void main(String[] args){
        SpringApplication.run(Main8002.class,args);
        System.out.println(SpringBootVersion.getVersion());
    }
}
