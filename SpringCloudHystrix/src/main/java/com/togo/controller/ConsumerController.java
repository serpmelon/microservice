package com.togo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.togo.service.ConsumerService;

@RestController
public class ConsumerController {

	@Autowired
	private ConsumerService service;

	@GetMapping("/dc")
	public String dc() {

		return service.consumer();
	}
}
