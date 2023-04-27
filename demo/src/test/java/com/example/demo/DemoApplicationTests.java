package com.example.demo;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class DemoApplicationTests {

    @Test
    void contextLoads() {
        int ans = 0;
        for(int i=0;i<100;i++){
            ans += i;
        }
        System.out.println(ans);
    }

}
