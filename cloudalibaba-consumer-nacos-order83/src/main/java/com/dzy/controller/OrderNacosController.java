package com.dzy.controller;


import com.dzy.api.PayFeignSentinelApi;
import com.dzy.resp.ResultData;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@Slf4j
public class OrderNacosController {


    @Resource
    private RestTemplate restTemplate;

    @Resource
    private PayFeignSentinelApi payFeignSentinelApi;

    @Value("${service-url.nacos-user-service}")
    private String serverURL;

    @GetMapping("/consumer/pay/nacos/{id}")
    public String paymentInfo(@PathVariable("id") Integer id)
    {
        String result = restTemplate.getForObject(serverURL + "/pay/nacos/" + id, String.class);
        return result+"\t"+"    我是OrderNacosController83调用者。。。。。。";
    }

    @GetMapping(value = "/consumer/pay/nacos/get/{orderNo}")
    public ResultData getPayOrderNo(@PathVariable("orderNo")String orderNo){
            return payFeignSentinelApi.getPayByOrderNo(orderNo);
    }

}
