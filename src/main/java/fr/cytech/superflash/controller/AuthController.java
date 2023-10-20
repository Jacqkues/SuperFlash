package fr.cytech.superflash.controller;

import fr.cytech.superflash.dto.UserDto;
import fr.cytech.superflash.entity.User;
import fr.cytech.superflash.service.UserService;

import java.util.List;

import org.springframework.stereotype.Controller;
import jakarta.validation.Valid;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AuthController {

	private UserService userService;
	
	public AuthController(UserService userService)
	{
		this.userService = userService;
	}
	
	@GetMapping("/accueil")
	public String accueil() {
		return "accueil";
	}
	
    // handler method to handle home page request
    @GetMapping("/index")
    public String home(){
        return "index";
    }
    
    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
    	UserDto user = new UserDto();
    	model.addAttribute("user", user);
    	return "register";
    }
    
    @PostMapping("/register/save")
    public String registration(@Valid @ModelAttribute("user") UserDto userDto, BindingResult result , Model model) {
    	User existingUser = userService.findUserByEmail(userDto.getEmail());
    	
    	if(existingUser != null && existingUser.getEmail() != null && !existingUser.getEmail().isEmpty()){
            result.rejectValue("email", null,
                    "There is already an account registered with the same email");
        }
    	
    	if(result.hasErrors()){
            model.addAttribute("user", userDto);
            return "/register";
        }
    	
    	userService.saveUser(userDto);
        return "redirect:/register?success";
    }
    
    
    @GetMapping("/users")
    public String users(Model model){
        List<UserDto> users = userService.findAllUsers();
        model.addAttribute("users", users);
        return "users";
    }
    
    @GetMapping("/login")
    public String login(){
        return "login";
    }
}