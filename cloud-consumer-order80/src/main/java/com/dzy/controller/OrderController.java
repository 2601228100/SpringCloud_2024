package com.dzy.controller;


import com.dzy.entities.PayDTO;
import com.dzy.resp.ResultData;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;


@RestController
@Slf4j
@Tag(name = "支付微服务消费模块",description = "支付CRUD")
public class OrderController {

//    public  static  final String paymentSrv_URL = "http://localhost:8001";
    public  static  final String paymentSrv_URL = "http://cloud-payment-service";

    @Resource
    private RestTemplate restTemplate;

    @GetMapping(value = "/consumer/pay/add")
    @Operation(summary = "新增",description = "新增支付流水方法,json串做参数")
    public ResultData addOrder(PayDTO payDTO){
        return restTemplate.postForObject(paymentSrv_URL + "/pay/add", payDTO, ResultData.class);
    }

    @GetMapping(value = "/consumer/pay/del/{id}")
    @Operation(summary = "删除",description = "删除支付流水方法,json串做参数")
    public  ResultData delOrder(@PathVariable("id")Integer id){
        return restTemplate.getForObject(paymentSrv_URL+"/pay/del/"+id , ResultData.class,id);
    }

    @GetMapping(value = "/consumer/pay/get/{id}")
    @Operation(summary = "查询",description = "查询支付流水方法,json串做参数")
    public  ResultData getOrder(@PathVariable("id")Integer id){
        return restTemplate.getForObject(paymentSrv_URL + "/pay/get/" + id, ResultData.class, id);
    }
    @GetMapping(value = "/consumer/pay/update")
    public ResultData updateOrder(PayDTO payDTO){
          return restTemplate.postForObject(paymentSrv_URL + "/pay/update", payDTO ,ResultData.class);
    }

    @GetMapping(value = "/consumer/pay/get/info")
    private  String getInfoByConsul(){
        return restTemplate.getForObject(paymentSrv_URL+"/pay/get/info",String.class);
    }


    @Resource
    private DiscoveryClient discoveryClient;

    @GetMapping("/consumer/discovery")
    public String discovery()
    {
        List<String> services = discoveryClient.getServices();
        for (String element : services) {
            System.out.println(element);
        }

        System.out.println("===================================");

        List<ServiceInstance> instances = discoveryClient.getInstances("cloud-payment-service");
        for (ServiceInstance element : instances) {
            System.out.println(element.getServiceId()+"\t"+element.getHost()+"\t"+element.getPort()+"\t"+element.getUri());
        }
        return instances.get(0).getServiceId()+":"+instances.get(0).getPort();
    }



}
