package com.ayao.product_service.service.impl;

import com.ayao.product_service.dao.itemDoMapper;
import com.ayao.product_service.dataobject.ItemDo;
import com.ayao.product_service.service.ItemService;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author ：ayao
 * @date ：Created in 2019/7/6 14:07
 * @version:
 */
@Service
@CacheConfig(cacheNames = "item")
public class ItemServiceImpl implements ItemService {
  @Resource
  private itemDoMapper itemDoMapper;
  @Override
  @CachePut
  public int createItem(ItemDo itemModel) {
    return itemDoMapper.insert(itemModel);
  }

  @Override
  @Cacheable
  public List<ItemDo> listItemByPage(Integer pageNum, Integer pageSize) {
    return itemDoMapper.listItemByPage(pageNum,pageSize);
  }

  @Override
  @Cacheable
  public ItemDo getItemById(Integer id) {
    return itemDoMapper.selectByPrimaryKey(id);
  }

  @Override
  public boolean decreaseStock(Integer itemId, Integer amount) {
    return false;
  }

  @Override
  public void increaseSales(Integer itemId, Integer amount) {

  }
}