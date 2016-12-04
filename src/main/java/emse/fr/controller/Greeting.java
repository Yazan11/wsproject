package emse.fr.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class Greeting {

	@RequestMapping("/greeting/{id}")
	public @ResponseBody String greeting(@PathVariable("id") String message){
		return "hello" + message;
	}
	
}
