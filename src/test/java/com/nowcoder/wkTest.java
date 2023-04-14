package com.nowcoder;

import java.io.IOException;

/**
  * @ClassName wkTest
  * @description: TODO
  * @author Hwangjj
  * @date 2023/4/14 19:56
  * @version: 1.0
  */ 

public class wkTest {

    public static void main(String[] args) {
        String cmd = "d:/hjj/wkhtmltopdf/bin/wkhtmltoimage --quality 75 https://www.nowcoder.com d:/hjj/wkhtmltopdf/wk-images/3.png";
        try {
            Runtime.getRuntime().exec(cmd);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
