package com.ayao.stream_producer.producer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author ：ayao
 * @date ：Created in 2019/11/29 21:09
 * @version:
 */
@Component
@EnableBinding(Source.class)
public class MessageSender {

  @Resource
  private MessageChannel output;
  //发送消息

  public void send(Object obj) {
    output.send(MessageBuilder.withPayload(obj).build());
  }
}
