package app.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {
	
	Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	public class Test {
		
		private String param;

		public String getParam() {
			return param;
		}

		public void setParam(String param) {
			this.param = param;
		}

		
		
	}
	
	@PostMapping(value = "/hello")
	public String test() {
		
//		logger.debug("{}", params);
		
		return "123";
	}
	
	@GetMapping(value = "/")
	public String home() {
		return "hello";
	}

	@GetMapping(value = "/private")
	public String privateArea() {
		return "private";
	}
	
}
