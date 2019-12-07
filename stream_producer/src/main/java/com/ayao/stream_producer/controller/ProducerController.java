package com.ayao.stream_producer.controller;

import com.ayao.stream_producer.producer.MessageSender;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * @author ：ayao
 * @date ：Created in 2019/11/29 21:36
 * @version:
 */
@Controller
@RequestMapping("/producer")
public class ProducerController {
  @Resource
  private MessageSender messageSender;

  @PostMapping("/send")
  @ResponseBody
  public void sendMessage(){ messageSender.send("gewNISAGJEWjggase");
  }


}
