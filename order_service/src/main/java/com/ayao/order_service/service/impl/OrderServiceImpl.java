package com.ayao.order_service.service.impl;


import com.ayao.order_service.dao.OrderDoMapper;
import com.ayao.order_service.dataobject.OrderDo;
import com.ayao.order_service.service.OrderService;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CachePut;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author ：ayao
 * @date ：Created in 2019/7/6 20:39
 * @version:
 */
@Service
@CacheConfig(cacheNames = "order")
public class OrderServiceImpl implements OrderService {
  @Resource
  private OrderDoMapper orderDoMapper;


  @Override
  @CachePut
  public int createOrder(OrderDo orderDo){

    return orderDoMapper.insert(orderDo);
  }
}
