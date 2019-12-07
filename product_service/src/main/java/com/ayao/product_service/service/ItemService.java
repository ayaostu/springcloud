package com.ayao.product_service.service;


import com.ayao.product_service.dataobject.ItemDo;

import java.util.List;

public interface ItemService {
  /**
   * 创建商品
   */
  int createItem(ItemDo itemModel);
  /**
   * 商品列表浏览
   */
  List<ItemDo> listItemByPage(Integer pageNum, Integer pageSize);
  /**
   * 商品详情页浏览
   */
  ItemDo getItemById(Integer id);

  /**
   * 库存扣减
   * @param itemId
   * @param amount
   * @return
   */
  boolean decreaseStock(Integer itemId, Integer amount);

  /**
   * 商品销量增加
   * @param itemId
   * @param amount
   */
  void increaseSales(Integer itemId, Integer amount);
}
