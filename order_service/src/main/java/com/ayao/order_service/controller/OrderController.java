package com.ayao.order_service.controller;

import com.ayao.order_service.dao.SequenceDoMapper;
import com.ayao.order_service.dataobject.OrderDo;
import com.ayao.order_service.dataobject.SequenceDo;
import com.ayao.order_service.entity.ItemDo;
import com.ayao.order_service.entity.UserDo;
import com.ayao.order_service.error.BusinessException;
import com.ayao.order_service.error.EmBusinessError;
import com.ayao.order_service.feign.ProductFeignClient;
import com.ayao.order_service.feign.UserFeignClient;
import com.ayao.order_service.response.CommonReturnType;
import com.ayao.order_service.service.OrderService;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author ：ayao
 * @date ：Created in 2019/11/23 19:58
 * @version:
 */
@Controller
@RequestMapping("/order")
public class OrderController{
  @Resource
  private SequenceDoMapper sequenceDoMapper;

  @Resource
  private ProductFeignClient productFeignClient;
  @Resource
  private HttpServletRequest httpServletRequest;
  @Resource
  private OrderService orderService;
  @Resource
  private UserFeignClient userFeignClient;


  @RequestMapping(value = "/buy",method = RequestMethod.GET)
  @ResponseBody
  public ItemDo query(@RequestParam("itemId") Integer itemId){
    ItemDo itemDo = null;
    itemDo = productFeignClient.queryById(itemId);
    return itemDo;
  }

  @RequestMapping(value = "/find",method = RequestMethod.GET)
  @ResponseBody
  public UserDo find(@RequestParam("id") Integer id){
    return userFeignClient.query(id);
  }
  /**
   * 创建订单
   */
  @RequestMapping(value = "/createorder",method = RequestMethod.POST)
  @ResponseBody
  public String createOrder(@RequestParam("itemId") Integer itemId,
                         @RequestParam("amount") Integer amount) throws BusinessException {

    Integer userId = (Integer) httpServletRequest.getSession().getAttribute("userId");
    UserDo userDo = userFeignClient.query(userId);
    //校验入参
    if (userDo == null) {
      throw new BusinessException(EmBusinessError.USER_NOT_LOGIN);
    }
    ItemDo itemDo = productFeignClient.queryById(itemId);
    OrderDo orderDo = new OrderDo();
    orderDo.setUserId(userDo.getId());
    orderDo.setItemId(itemDo.getId());
    orderDo.setItemPrice(itemDo.getPrice());
    orderDo.setAmount(amount);
    orderDo.setOrderPrice(BigDecimal.valueOf(itemDo.getPrice()).multiply(BigDecimal.valueOf(amount)).doubleValue());
    //订单入库
    orderDo.setId(generateOrder());
    orderService.createOrder(orderDo);
    return "下单成功";
  }
  @Transactional(propagation = Propagation.REQUIRES_NEW)
  public String generateOrder(){
    //生成16为订单号
    StringBuilder sb = new StringBuilder(16);
    //前8位位时间信息，年月日
    LocalDateTime now = LocalDateTime.now();
    String nowDate = now.format(DateTimeFormatter.ISO_DATE).replace("-","");
    sb.append(nowDate);

    //中间6位为自增序列
    //获取当前sequence
    int sequence = 0;
    SequenceDo sequenceDo = sequenceDoMapper.selectByPrimaryKey("order_info");
    sequence = sequenceDo.getCurrentValue();
    sequenceDo.setCurrentValue(sequenceDo.getCurrentValue() + sequenceDo.getStep());
    sequenceDoMapper.updateByPrimaryKeySelective(sequenceDo);
    String sequenceStr = String.valueOf(sequence);
    //若序列号不足六位补0
    for (int i = 0; i < 6 - sequenceStr.length(); i++) {
      sb.append(0);
    }
    sb.append(sequenceStr);

    //最后两位为分库分表位,这里锁定
    sb.append("00");
    return sb.toString();
  }

}
