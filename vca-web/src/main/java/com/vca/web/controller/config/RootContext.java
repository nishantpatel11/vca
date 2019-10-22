package com.vca.web.controller.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;


@Configuration
@ComponentScan(basePackages = {"com.vca"})
@Import({WebMvcConfig.class})
public class RootContext {

}
