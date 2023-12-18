package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class BotController {
	@GetMapping
	public String home() {
		return "home";
	}
}
