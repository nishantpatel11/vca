package com.vca.service.impl;

import org.springframework.stereotype.Service;

import com.vca.service.HelloWorldService;

@Service
public class HelloWorldServiceImpl implements HelloWorldService{

	public String sayHello(String message) {

		return message;
	}

}
