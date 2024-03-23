package com.dzy.controller;


import cn.hutool.core.date.DateUtil;
import com.dzy.api.PayFeignApi;
import com.dzy.entities.PayDTO;
import com.dzy.resp.ResultData;
import com.dzy.resp.ReturnCodeEnum;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;


@RestController
@Slf4j
public class OrderController {

    @Resource
    private PayFeignApi payFeignApi;


    @PostMapping(value = "/feign/pay/add")
    public ResultData  addPay(@RequestBody PayDTO payDTO){
        ResultData resultData = payFeignApi.addPay(payDTO);
        return resultData;
    }

    @DeleteMapping(value = "/feign/pay/del/{id}")
    public  ResultData delPay(@PathVariable("id")Integer id){
        ResultData resultData = payFeignApi.deletePay(id);
        return resultData;
    }


    @PutMapping(value = "/feign/pay/update")
    public  ResultData updatePay(@RequestBody PayDTO payDTO){
        ResultData resultData = payFeignApi.updatePay(payDTO);
        return resultData;
    }

    @GetMapping(value = "/feign/pay/get/{id}")
    public  ResultData getById(@PathVariable("id")Integer id){

        System.out.println("-------支付微服务远程调用，按照id查询订单支付流水信息");
        ResultData resultData = null;
        try{
            System.out.println("调用开始-----:"+ DateUtil.now());
            resultData = payFeignApi.getById(id);
        }catch (Exception e){
            e.printStackTrace();
            System.out.println("调用结束-----:"+DateUtil.now());
            ResultData.fail(ReturnCodeEnum.RC500.getCode(),e.getMessage());
        }
        return resultData;
    }

    @GetMapping(value = "/feign/pay/getInfo")
    public  String  getInfo(){
        String mylb = payFeignApi.mylb();
        return mylb;
    }

}
