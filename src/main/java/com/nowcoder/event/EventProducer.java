package com.nowcoder.event;

import com.alibaba.fastjson2.JSONObject;
import com.nowcoder.entity.Event;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

/**
  * @ClassName EventProducer
  * @description: TODO
  * @author Hwangjj
  * @date 2023/4/12 14:55
  * @version: 1.0
  */ 
@Component
public class EventProducer {

    @Autowired
    private KafkaTemplate kafkaTemplate;

    // 处理事件
    public void fireEvent(Event event){
        // 将事件发布到指定的主题
        kafkaTemplate.send(event.getTopic(), JSONObject.toJSONString(event));
    }
}
