package com.ayao.order_service.dao;

import com.ayao.order_service.dataobject.OrderDo;

public interface OrderDoMapper {
    int deleteByPrimaryKey(String id);

    int insert(OrderDo record);

    int insertSelective(OrderDo record);

    OrderDo selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(OrderDo record);

    int updateByPrimaryKey(OrderDo record);
}