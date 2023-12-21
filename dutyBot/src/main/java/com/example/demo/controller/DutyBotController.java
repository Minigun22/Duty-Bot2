package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/dutybot")
public class DutyBotController {

	@GetMapping("/send")
	public String sendPage() {
		return "send";
	}
}
