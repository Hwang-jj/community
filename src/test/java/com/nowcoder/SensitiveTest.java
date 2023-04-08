package com.nowcoder;

import com.nowcoder.util.SensitiveFilter;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;


/**
  * @ClassName SensitiveTest
  * @description: TODO
  * @author Hwangjj
  * @date 2023/4/8 16:39
  * @version: 1.0
  */
@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = CommunityApplication.class)
public class SensitiveTest {

    @Autowired
    private SensitiveFilter sensitiveFilter;

    @Test
    public void testSensitiveFilter(){
        String text = "这里可以赌博、可以嫖娼、可以吸毒、可以开票，哈哈哈！";
        String filter = sensitiveFilter.filter(text);
        System.out.println(filter);

        text = "这里可以☆赌☆博☆、可以☆嫖☆娼☆、可以☆吸☆毒☆、可以☆开☆票☆，哈哈哈！";
        System.out.println(sensitiveFilter.filter(text));
    }
}
