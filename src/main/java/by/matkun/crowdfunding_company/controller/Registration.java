package by.matkun.crowdfunding_company.controller;

import by.matkun.crowdfunding_company.model.Role;
import by.matkun.crowdfunding_company.model.User;
import by.matkun.crowdfunding_company.service.UserServiceImplement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Collections;
import java.util.Map;

@Controller
public class Registration {
    @Autowired
    private  UserServiceImplement userService;

    @GetMapping("/registration")
    public String registration(){
        return "registration";
    }

    @PostMapping("/registration")
    public String addNewUser(User user, Map<String, Object> model) {
        User userFromDb = userService.findByName(user.getUsername());
        if (userFromDb != null) {
            model.put("message", "User exist");
        } else {
            user.setRoles(Collections.singleton(Role.USER));
            userService.save(user);
            return "redirect:/login";
        }
        return "registration";
    }
}
