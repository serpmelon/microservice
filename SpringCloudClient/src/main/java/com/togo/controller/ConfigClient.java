package com.togo.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RefreshScope
@RestController
public class ConfigClient {

//	@Value("${name}")
	private String name;
	@Value("${info.profile}")
	private String info;
	
	@GetMapping("/test")
	public String test() {
		
		System.out.println(name);
		System.out.println(info);
		return name + info;
	}
}
