package com.ayao.stream_producer;

import com.ayao.stream_producer.producer.MessageSender;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@RunWith(SpringRunner.class)
@SpringBootTest
public class StreamProducerApplicationTests {
  @Resource
  private MessageSender messageSender;

  @Test
  public void contextLoads() {
  messageSender.send("dsgjewpjigj");
  }

}
