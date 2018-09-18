package app.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProducerController {

	@GetMapping("employee")
	public String hi() {
		return "this is an employee";
	}
	
}
