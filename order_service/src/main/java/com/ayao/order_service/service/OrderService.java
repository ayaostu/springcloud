package com.ayao.order_service.service;


import com.ayao.order_service.dataobject.OrderDo;
import com.ayao.order_service.error.BusinessException;

public interface OrderService {
  /**
   * 创建订单
   *
   * @return
   */
  int createOrder(OrderDo orderDo) throws BusinessException;
}
