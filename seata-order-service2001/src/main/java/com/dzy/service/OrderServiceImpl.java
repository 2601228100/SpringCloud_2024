package com.dzy.service;

import com.dzy.api.AccountFeignApi;
import com.dzy.api.StorageFeignApi;
import com.dzy.entities.Order;
import com.dzy.mapper.OrderMapper;
import io.seata.core.context.RootContext;
import io.seata.spring.annotation.GlobalTransactional;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;
/**
 * @auther zzyy
 * @create 2023-12-01 17:53
 * 下订单->减库存->扣余额->改(订单)状态
 */
@Slf4j
@Service
public class OrderServiceImpl implements OrderService
{
    @Resource
    private  OrderMapper  orderMapper;
    //订单为服务通过openfeing去调用账户微服务
    @Resource
    private   AccountFeignApi accountFeignApi;
    //订单为服务通过openfeing去调用库存微服务
    @Resource
    private  StorageFeignApi storageFeignApi;
    /**
     * 创建订单
     *
     * @param order
     */
    @Override
    @GlobalTransactional(name = "dzy-create-order",rollbackFor = Exception.class) //AT
    public void create(Order order) {
          //XID 全局事务id的检查
        String xid = RootContext.getXID();
        //1，新建订单
        log.info("-------------------开始新建订单"+"\t"+xid);
        //默认的开始的是时候的订单的状态是0
        order.setStatus(0);
        int result = orderMapper.insertSelective(order);
        //插入实体之后获得插入的mysql的实体
        Order orderTemp = null;
        if (result>0){
            orderTemp= orderMapper.selectOne(order);
            log.info("-------------------新建订单成功+orderTemp info ：" + orderTemp);
            System.out.println();
            //2.扣减库存
            log.info("-------> 订单微服务开始调用Storage库存，做扣减count");
            storageFeignApi.decrease(orderTemp.getProductId(), orderTemp.getCount());
            log.info("-------> 订单微服务结束调用Storage库存，做扣减完成");
            System.out.println();
            //3.扣减用户账户的余额
            log.info("-------> 订单微服务开始调用Account账号，做扣减money");
            accountFeignApi.decrease(orderTemp.getUserId(), orderTemp.getMoney());
            log.info("-------> 订单微服务结束调用Account账号，做扣减完成");
            System.out.println();
            //4. 修改订单状态
            //订单状态status：0：创建中；1：已完结
            log.info("-------> 修改订单状态");
            orderTemp.setStatus(1);


            Example whereCondition = new Example(Order.class);
            Example.Criteria criteria = whereCondition.createCriteria();
            criteria.andEqualTo("userId",orderTemp.getUserId());
            criteria.andEqualTo("status",0);

            int updateResult = orderMapper.updateByExampleSelective(orderTemp, whereCondition);
            log.info("-------> 修改订单状态完成"+"\t"+updateResult);
            log.info("-------> orderFromDB info: "+orderTemp);
        }

        System.out.println();
        log.info("---------结束新建订单"+"\t"+xid);

    }
}
