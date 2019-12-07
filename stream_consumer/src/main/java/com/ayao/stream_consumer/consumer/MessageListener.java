package com.ayao.stream_consumer.consumer;

import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.stereotype.Component;


/**
 * @author ：ayao
 * @date ：Created in 2019/11/29 21:32
 * @version:
 */
@Component
@EnableBinding(Sink.class)
public class MessageListener {

  @StreamListener(Sink.INPUT)
  public void input(String message){
    System.out.println("获取到消息:"+message);
  }

}
