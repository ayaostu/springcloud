package com.ayao.product_service.dao;

import com.ayao.product_service.dataobject.ItemDo;

import java.util.List;

public interface itemDoMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ItemDo record);

    int insertSelective(ItemDo record);

    ItemDo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ItemDo record);

    int updateByPrimaryKey(ItemDo record);
    List<ItemDo> listItemByPage(Integer pageNum, Integer pageSize);

}