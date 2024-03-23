package com.dzy.controller;


import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.IdUtil;
import com.dzy.entities.Pay;
import com.dzy.resp.ResultData;
import com.dzy.servcie.PayService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Enumeration;

@RestController
public class PayGateWayController {
    @Resource
    PayService payService;

    @GetMapping(value = "/pay/gateway/get/{id}")
    public ResultData<Pay> getById(@PathVariable("id") Integer id)
    {
        Pay pay = payService.getById(id);
        return ResultData.success(pay);
    }

    @GetMapping(value = "/pay/gateway/info")
    public ResultData<String> getGatewayInfo()
    {
        return ResultData.success("gateway info test："+ IdUtil.simpleUUID());
    }


    @GetMapping(value = "/pay/gateway/filter")
    public ResultData<String> getGatewayFilter(HttpServletRequest request)
    {
        String result = "";
        Enumeration<String> headers = request.getHeaderNames();
        while(headers.hasMoreElements())
        {
            String headName = headers.nextElement();
            String headValue = request.getHeader(headName);
            System.out.println("请求头名: " + headName +"\t\t\t"+"请求头值: " + headValue);
            if(headName.equalsIgnoreCase("X-Request-atguigu1")
                    || headName.equalsIgnoreCase("X-Request-atguigu2")) {
                result = result+headName + "\t " + headValue +" ";
            }
        }


        System.out.println("=============================================");
        String red = request.getParameter("red");
        System.out.println("request Parameter red: "+red);

        String blue = request.getParameter("blue");
        System.out.println("request Parameter blue: "+blue);

        String green = request.getParameter("green");
        System.out.println("request Parameter green: "+green);
        System.out.println("=============================================");


        return ResultData.success("getGatewayFilter 过滤器 test： "+result+" \t "+ DateUtil.now());
    }

}
