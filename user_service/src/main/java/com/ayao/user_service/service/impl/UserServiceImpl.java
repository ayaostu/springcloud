package com.ayao.user_service.service.impl;

import com.ayao.user_service.dao.UserDoMapper;
import com.ayao.user_service.dataobject.UserDo;
import com.ayao.user_service.error.BusinessException;
import com.ayao.user_service.error.EmBusinessError;
import com.ayao.user_service.service.UserService;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author ：ayao
 * @date ：Created in 2019/11/30 10:29
 * @version:
 */
@Service
@CacheConfig(cacheNames = "user2")
public class UserServiceImpl implements UserService {

  @Resource
  private UserDoMapper userDoMapper;

  @Override
  @Cacheable
  public UserDo query(Integer id) {
    return userDoMapper.selectByPrimaryKey(id);
  }
  @Override
  @CachePut
  public int createuser(UserDo userDo) {
    return userDoMapper.insert(userDo);
  }

  @Override
  @CacheEvict
  public int deluser(Integer id) {
    return userDoMapper.deleteByPrimaryKey(id);
  }

  @Override
  @CachePut
  public int updateuser(UserDo userDo) {
    return userDoMapper.updateByPrimaryKey(userDo);
  }

  /**
   * 简单实现，此处有省略
   * @param userName
   * @param password
   * @return
   */
  @Override
  @CachePut
  public UserDo login(String userName,String password) throws BusinessException {
    UserDo userDo = userDoMapper.selectByUserName(userName);
    if (userDo == null) {
      throw new BusinessException(EmBusinessError.USER_NOT_EXIST);
    }
    //比对用户名和密码是否和传输进来的匹配
    if (!password.equals(userDo.getPassword())){
      throw new BusinessException(EmBusinessError.USER_LOGIN_FAIL);
    }
    return userDo;
  }
}
