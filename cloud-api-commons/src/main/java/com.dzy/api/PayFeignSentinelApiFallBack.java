package com.dzy.api;


import com.dzy.resp.ResultData;
import com.dzy.resp.ReturnCodeEnum;
import org.springframework.stereotype.Component;

@Component
public class PayFeignSentinelApiFallBack implements PayFeignSentinelApi{

    @Override
    public ResultData getPayByOrderNo(String orderNo) {
        return  ResultData.fail(ReturnCodeEnum.RC500.getCode(), "对方的服务宕机或是不可以使用，现在进行FullBack的降级处理 o(╥﹏╥)o");
    }

}
