package by.matkun.crowdfunding_company.controller;

import by.matkun.crowdfunding_company.model.User;
import by.matkun.crowdfunding_company.service.CompanyServiceImplement;
import by.matkun.crowdfunding_company.service.UserServiceImplement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class MainController {

    @Autowired
    private UserServiceImplement userServiceImplement;

    @Autowired
    private CompanyServiceImplement companyService;

    @GetMapping
    public String getListCompany(@AuthenticationPrincipal User currentUser, Model model){
        model.addAttribute("companyList",companyService.findAll());
        if (currentUser==null){
            model.addAttribute("currentUser",null);
        }else {
            User user = userServiceImplement.findByName(currentUser.getUsername());
            model.addAttribute("currentUser", user);
        }
        return "main";
    }
}
