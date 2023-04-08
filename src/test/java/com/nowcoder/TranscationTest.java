package com.nowcoder;

import com.nowcoder.service.HelloService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

/**
  * @ClassName TranscationTest
  * @description: TODO
  * @author Hwangjj
  * @date 2023/4/8 21:26
  * @version: 1.0
  */
@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = CommunityApplication.class)
public class TranscationTest {

    @Autowired
    private HelloService helloService;

    @Test
    public void testSave1(){
        Object obj = helloService.save1();
        System.out.println(obj);
    }

    @Test
    public void testSave2(){
        Object obj = helloService.save2();
        System.out.println(obj);
    }
}
