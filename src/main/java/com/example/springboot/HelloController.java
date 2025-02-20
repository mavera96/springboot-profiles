package com.example.springboot;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {
	@Value("${liverpool.message}")
	private String message;
	@GetMapping("/")
	public String index() {
		return message;
	}
}
