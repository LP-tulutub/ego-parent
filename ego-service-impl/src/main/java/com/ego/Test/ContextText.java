package com.ego.Test;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;

public class ContextText {
    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath*:applicationContext-dubbo.xml");
        context.start();
        System.out.println("dubbo provider start...");

        try {
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
