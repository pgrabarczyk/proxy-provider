package com.pgrabarczyk.proxy.provider;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@ComponentScan(basePackages = "com.pgrabarczyk.proxy.provider")
@PropertySource("classpath:application.properties")
public class AppConf {

}
