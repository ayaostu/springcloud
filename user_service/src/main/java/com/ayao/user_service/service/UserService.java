package com.ayao.user_service.service;

import com.ayao.user_service.dataobject.UserDo;
import com.ayao.user_service.error.BusinessException;
import org.apache.ibatis.annotations.Param;

public interface UserService {
  /**
   * 根据ID查询
   * @param id
   * @return
   */
  UserDo query(Integer id);
  /**
   * 添加
   */
  int createuser(UserDo userDo);
  /**
   * 根据ID删除用户
   */
  int deluser(Integer id);
  /**
   * 根据更新用户
   */
  int updateuser(UserDo userDo);

  /**
   * 用户登录
   */
  UserDo login(@Param("userName") String userName, @Param("password") String password) throws BusinessException;

}
