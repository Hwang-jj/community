package com.nowcoder;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

/**
  * @ClassName KafkaTest
  * @description: TODO
  * @author Hwangjj
  * @date 2023/4/12 14:17
  * @version: 1.0
  */
@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = CommunityApplication.class)
public class KafkaTest {

    @Autowired
    private KafkaProducer kafkaProducer;


    /*
    D:\hjj\kafka_2.12-2.2.0>bin\windows\zookeeper-server-start.bat config\zookeeper.properties
    D:\hjj\kafka_2.12-2.2.0>bin\windows\kafka-server-start.bat config\server.properties
    测试之前要启动zookeeper和kafka
     */

    @Test
    public void testKafka(){
        kafkaProducer.send("test", "你好");
        kafkaProducer.send("test", "在吗");
        kafkaProducer.send("test", "再见");

        try {
            Thread.sleep(1000 * 10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
@Component
class KafkaProducer{

    @Autowired
    private KafkaTemplate kafkaTemplate;

    public void send(String topic, String content){
        kafkaTemplate.send(topic, content);
    }

}

@Component
class KafkaConsumer{

    @KafkaListener(topics = {"test"})
    public void handleMessage(ConsumerRecord record){
        System.out.println(record.value());
    }


}
