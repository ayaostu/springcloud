package com.ayao.order_service.feign;

import com.ayao.order_service.entity.ItemDo;
import org.springframework.stereotype.Component;

/**
 * @author ：ayao
 * @date ：Created in 2019/11/24 13:40
 * @version:
 */
@Component
public class ProductFeignClientCallBack implements ProductFeignClient {

  /**
   * 熔断降级的方法
   * @param itemId
   * @return
   */
  public ItemDo queryById(Integer itemId) {
    ItemDo itemDo = new ItemDo();
    itemDo.setDescription("feign调用触发了熔断降级方法");
    return itemDo;
  }
}
