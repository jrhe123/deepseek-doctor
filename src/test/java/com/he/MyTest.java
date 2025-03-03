package com.he;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.StopWatch;

@SpringBootTest
public class MyTest {

    @Test
    public void testStopWatch() throws Exception {

        StopWatch stopWatch = new StopWatch();
        stopWatch.start("task1");
        Thread.sleep(1000);
        stopWatch.stop();

        stopWatch.start("task2");
        Thread.sleep(1000);
        stopWatch.stop();

        System.out.println(stopWatch.prettyPrint());
        System.out.println(stopWatch.shortSummary());

        System.out.println(stopWatch.getTotalTimeMillis());
        System.out.println(stopWatch.getTaskCount());
    }
}
