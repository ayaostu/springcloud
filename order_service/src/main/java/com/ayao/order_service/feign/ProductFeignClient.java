package com.ayao.order_service.feign;

import com.ayao.order_service.entity.ItemDo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 声明需要调用的微服务
 * @FeignClient
 *        name: 服务提供者的名称
 *        fallback:p配置熔断发生降级方法（实现类）
 */
@FeignClient(name = "service-product",fallback = ProductFeignClientCallBack.class)
public interface ProductFeignClient {
  /**
   * 配置需要调用的微服务接口
   * 服务中方法的映射路径
   */
  @RequestMapping(value = "/item/query",method = RequestMethod.GET)
   ItemDo queryById(@RequestParam("itemId") Integer itemId);
}
