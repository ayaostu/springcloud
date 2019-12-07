package com.ayao.user_service.error;

public interface CommonError {

  public int getErrCode();


  public String getErrMsg();


  public CommonError setErrMsg(String errMsg);


}
