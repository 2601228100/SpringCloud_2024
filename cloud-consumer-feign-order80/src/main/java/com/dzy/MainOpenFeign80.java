package com.dzy;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableDiscoveryClient //向consul注册信息
@EnableFeignClients//启用feign客户端
public class MainOpenFeign80 {
    public  static  void main(String[] args){
        SpringApplication.run(MainOpenFeign80.class,args);
    }
}
