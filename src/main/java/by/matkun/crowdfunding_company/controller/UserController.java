package by.matkun.crowdfunding_company.controller;

import by.matkun.crowdfunding_company.model.Role;
import by.matkun.crowdfunding_company.model.User;
import by.matkun.crowdfunding_company.service.UserServiceImplement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Arrays;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/admin")
@PreAuthorize("hasAuthority('ADMIN')")
public class UserController {
    @Autowired
    private UserServiceImplement userService;

    @GetMapping
    public String userList(Principal principal,Model model) {
        User currentUser = (User) userService.loadUserByUsername(principal.getName());
        model.addAttribute("currentUser",currentUser);
        model.addAttribute("userList", userService.findAll());
        return "adminPage";
    }

    @GetMapping({"/user/{userId}"})
    public String editUser(Principal principal,@PathVariable(name = "userId") User user, Model model) {
        User currentUser = (User) userService.loadUserByUsername(principal.getName());
        model.addAttribute("currentUser",currentUser);
        model.addAttribute("user", user);
        model.addAttribute("roles", Role.values());
        return "editUser";
    }

    @PostMapping("/user/{userId}")
    public String updateUser(@RequestParam Map<String,String> form, User user) {
        User userFromDb = userService.find(user.getId());
        userService.setRole(userFromDb,form);
        userService.save(userFromDb);
        return "redirect:/admin";
    }
}
