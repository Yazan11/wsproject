package emse.fr.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import emse.fr.model.Person;

@Controller
public class PersonController {
	
	@RequestMapping("/person")
	public String person(Model model){
		Person p = new Person();
		p.setFirstName("joseph");
		p.setLastName("kamel");
		p.setAge(23);
		
		model.addAttribute("person",p);
		return "personview";
	}

}
