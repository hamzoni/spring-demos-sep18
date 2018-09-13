package app.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import app.api.ApiVersion;


@RestController
@RequestMapping("test")
@ApiVersion({"1"})	
public class TestController {

	@GetMapping
	@ApiVersion("")
	public String test() {
		return "test";
	}
	
	@ApiVersion("2")
	@GetMapping()
	public String test2() {
		return "test2";
	}
	
}
