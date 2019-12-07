package com.ayao.order_service.feign;

import com.ayao.order_service.entity.UserDo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "service-user",fallback = UserFeignClientCallBack.class)
public interface UserFeignClient {
  @RequestMapping(value = "/user/query",method = RequestMethod.GET)
  UserDo query(@RequestParam("id") Integer id);
  @RequestMapping(value = "/user/login",method = RequestMethod.POST)
  UserDo login(@RequestParam("userName") String userName,@RequestParam("password") String password);
}
