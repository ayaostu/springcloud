package com.ayao.order_service.response;

public class CommonReturnType {

  //若status=success，则data内返回前端需要的json数据
  //若status=fail， 则data内使用通用的错误码格式
  private Object data;
  //表明对应请求的返回处理结果是success或fail
  private String status;

  //定义一个通用的创建方法
  public static CommonReturnType create(Object result) {
    return CommonReturnType.create(result, "success");
  }


  public static CommonReturnType create(Object result, String status) {
    CommonReturnType type = new CommonReturnType();
    type.setStatus(status);
    type.setData(result);
    return type;
  }

  public Object getData() {
    return data;
  }

  public void setData(Object data) {
    this.data = data;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }


}
