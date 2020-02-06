package by.matkun.crowdfunding_company.controller;

import by.matkun.crowdfunding_company.model.Role;
import by.matkun.crowdfunding_company.model.User;
import by.matkun.crowdfunding_company.service.UserServiceImplement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/admin")
@PreAuthorize("hasAuthority('ADMIN')")
public class AdminController {
    @Autowired
    UserServiceImplement userService;

    @GetMapping
    public String userList(Model model) {
        model.addAttribute("userList", userService.findAll());
        return "adminPage";
    }

    @GetMapping({"/user/{userId}"})
    public String editUser(@PathVariable(name = "userId") User user, Model model) {
        model.addAttribute("user", user);
        model.addAttribute("roles", Role.values());
        return "editUser";
    }

    @PostMapping("/user/{userId}")
    public String updateUser(@RequestParam Map<String,String> form, User user) {
        User userFromDb = userService.find(user.getId());
        userFromDb.setName(user.getUsername());
        Set<String> roles = Arrays.stream(Role.values())
                .map(Role::name)
                .collect(Collectors.toSet());
        userFromDb.getRoles().clear();
        for (String key: form.keySet()) {
            if (roles.contains(key)){
                userFromDb.getRoles().add(Role.valueOf(key));
            }
        }
        userService.save(userFromDb);

        return "redirect:/admin";
    }
}
