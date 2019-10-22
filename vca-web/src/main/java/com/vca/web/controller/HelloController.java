package com.vca.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vca.service.HelloWorldService;

@RestController
@RequestMapping("/hello")
public class HelloController {

	@Autowired
	private HelloWorldService helloService;

	
	@GetMapping("/says")
	public String sayHelloWorld() {
		return helloService.sayHello("Hello World");
	}

	@GetMapping("/say")
	public String sayHello() {
		return "Hello";
	}

	
}
