package top.xsword.system_service;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.Arrays;
import java.util.HashMap;
import java.util.stream.Stream;

/**
 * Data:2022/11/19
 * Author:ywx
 * Description:
 */
@EnableScheduling
@EnableAspectJAutoProxy
@MapperScan(basePackages = "top.xsword.system_service.mapper") //指定mapper所在包的路径
@SpringBootApplication(scanBasePackages = "top.xsword")
public class Application {
    public static void main(String[] args) {

        ConfigurableApplicationContext run = SpringApplication.run(Application.class, args);
//        Arrays.stream(run.getBeanDefinitionNames()).forEach(System.out::println);
    }
}
