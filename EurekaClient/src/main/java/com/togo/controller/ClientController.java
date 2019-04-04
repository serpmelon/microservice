package com.togo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ClientController {

	@Autowired
	private DiscoveryClient discoveryClient;

	@GetMapping("/dc")
	public String dc() throws InterruptedException {

		String services = "Services: " + discoveryClient.getServices();
		System.out.println(services);

		return services;
	}
}
