package by.matkun.crowdfunding_company.controller;

import by.matkun.crowdfunding_company.model.User;
import by.matkun.crowdfunding_company.service.UserServiceImplement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;
import java.util.Map;

@Controller
public class RegistrationController {
    @Autowired
    private  UserServiceImplement userService;

    @GetMapping("/registration")
    public String registration(){
        return "registration";
    }

    @PostMapping("/registration")
    public String addNewUser(User user, Map<String, Object> model) {
        if (userService.addNewUser(user,model)!=null) {
            return "redirect:/login";
        }
        return "registration";
    }
}
