package net.javaguides.springboot.controller;

import org.hibernate.validator.internal.engine.ConstraintViolationImpl;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import net.javaguides.springboot.dto.UserRegistrationDto;
import net.javaguides.springboot.service.UserService;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

@Controller
@RequestMapping("/registration")
public class UserRegistrationController {

	private UserService userService;

	public UserRegistrationController(UserService userService) {
		super();
		this.userService = userService;
	}
	
	@ModelAttribute("user")
    public UserRegistrationDto userRegistrationDto() {
        return new UserRegistrationDto();
    }
	
	@GetMapping
	public String showRegistrationForm() {
		return "registration";
	}
	
	@PostMapping
	public String registerUserAccount(@ModelAttribute("user") UserRegistrationDto registrationDto) {
		try{
			userService.save(registrationDto);
		} catch (ConstraintViolationException e){

			String redirect = "redirect:/registration?";
			for (ConstraintViolation<?> error:
					e.getConstraintViolations()) {
				redirect = redirect.concat(error.getPropertyPath() + "=true&");
			}

			return redirect;
		}

		return "redirect:/registration?success";
	}
}
