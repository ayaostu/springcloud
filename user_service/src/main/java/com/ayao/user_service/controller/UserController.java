package com.ayao.user_service.controller;

import com.ayao.user_service.dataobject.UserDo;
import com.ayao.user_service.error.BusinessException;
import com.ayao.user_service.error.EmBusinessError;
import com.ayao.user_service.service.UserService;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @author ：ayao
 * @date ：Created in 2019/11/24 11:00
 * @version:
 */
@Controller
@RequestMapping("/user")
public class UserController{

  @Resource
  private UserService userService;
  @Resource
  private HttpServletRequest httpServletRequest;

  /**
   * 用户注册
   */
  @PostMapping("/register")
  @ResponseBody
  public int createUser(@RequestParam("userName") String userName,
                           @RequestParam("telphone") String telphone,
                           @RequestParam("age") Integer age,
                           @RequestParam("gender") Integer gender,
                           @RequestParam("password") String password){
    UserDo userDo = new UserDo();
    userDo.setUserName(userName);
    userDo.setAge(age);
    userDo.setGender(gender);
    userDo.setPassword(md5(password));
    userDo.setTelphone(telphone);
    return userService.createuser(userDo);
  }
  /**
   * 加盐md5加密密码
   * @param password
   * @return
   */
  private String md5(String password) {
    String salt = "sdfsf4et257*%^s';lddwsg9";
    String base = password +"/"+ salt;
    return DigestUtils.md5DigestAsHex(base.getBytes());
  }

  /**
   * 用户登录
   */
  @ResponseBody
  @PostMapping(value = "/login")
  public int login(@RequestParam("userName") String userName,
                      @RequestParam("password") String password) throws BusinessException {
    //入参校验
    if (org.apache.commons.lang.StringUtils.isEmpty(userName) || StringUtils.isEmpty(password)){
      throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR);
    }
    //登录服务,校验用户是否合法
    UserDo userDo = userService.login(userName,md5(password));
    //将用户登录凭证加入到登录成功的session中
    httpServletRequest.getSession().setAttribute("userId",userDo.getId());
    return userDo.getId();
  }
//  @RequestMapping(value = "/get",method = RequestMethod.GET)
//  public UserDo get(HttpServletRequest httpSession){
//    UserDo userDo = (UserDo) httpSession.getAttribute("LOGIN_USER");
//    return userDo;
//  }
  /**
   * 用户查询
   */
  @RequestMapping(value = "/query",method = RequestMethod.GET)
  @ResponseBody
  public UserDo queryById(@RequestParam("id") Integer id){
    return userService.query(id);
  }
  /**
   * 用户修改
   */
//  @PostMapping("/update")
//  private int updateuser(@RequestParam("userName") String userName,
//                         @RequestParam("telphone") String telphone,
//                         @RequestParam("age") Integer age,
//                         @RequestParam("gender") Integer gender,
//                         @RequestParam("password") String password){
//
//  }
  /**
   * 用户删除
   */
  @PostMapping("/del")
  public int delUser(@RequestParam("id") Integer id){
    return userService.deluser(id);
  }
}
