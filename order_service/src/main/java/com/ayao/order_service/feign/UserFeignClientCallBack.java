package com.ayao.order_service.feign;

import com.ayao.order_service.entity.UserDo;
import org.springframework.stereotype.Component;

/**
 * @author ：ayao
 * @date ：Created in 2019/11/24 13:40
 * @version:
 */
@Component
public class UserFeignClientCallBack implements UserFeignClient {




  /**
   * 熔断降级的方法
   * @param
   * @return
   */
  public UserDo query(Integer id) {
    UserDo userDo = new UserDo();
    userDo.setUserName("feign调用触发了熔断降级方法");
    return userDo;
  }

  @Override
  public UserDo login(String userName, String password) {
    return null;
  }
}
