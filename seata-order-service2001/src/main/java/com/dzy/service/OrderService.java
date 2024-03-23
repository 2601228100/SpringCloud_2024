package com.dzy.service;


import com.dzy.entities.Order;

public interface OrderService {

    /**
     * 创建订单
     */
    void create(Order order);

}