package by.matkun.crowdfunding_company.controller;

import by.matkun.crowdfunding_company.model.Company;
import by.matkun.crowdfunding_company.model.User;
import by.matkun.crowdfunding_company.service.CompanyServiceImplement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("user/{userId}")
public class UserController {

    @Autowired
    private CompanyServiceImplement companyService;

    @GetMapping
    public String getListCompany(@AuthenticationPrincipal User currentUser, @PathVariable (name = "userId") User user, Model model){
        List<Company> listCompany = user.getCompanies();
        model.addAttribute("listCompany",listCompany);
        model.addAttribute("isCurrentUser",currentUser.equals(user));
        model.addAttribute("currentUser",user.getId());
        return "user";
    }

    @PostMapping
    public String createNewCompany(Company company, @RequestParam("file") MultipartFile file,
                                   @AuthenticationPrincipal User currentUser,
                                   @PathVariable (name = "userId")User user) throws IOException {
        company.setOwner(user);
        companyService.uploadImage(company,file);
        companyService.save(company);
        return "redirect:/user/{userId}";
    }
    @GetMapping("/editCompany/{companyId}")
    public String editCompany(@PathVariable (name = "companyId")Long companyId,@PathVariable (name = "userId") User user , Model model){
        model.addAttribute("company",companyService.find(companyId));
        model.addAttribute("currentUser",user);
        return "editCompany";
    }

    @PostMapping("/editCompany/{companyId}")
    public String updateCompany(Company company, @PathVariable(name = "companyId") Long companyId,@RequestParam Long owner){
        company.setId(companyId);
        companyService.save(company);
        return "redirect:/user/" + owner;
    }
    @PostMapping("/deleteCompany")
    public String deleteCompany(@RequestParam Long companyId, @PathVariable (name = "userId") User user){
        companyService.delete(companyId);
        return "redirect:/user/" + user.getId() ;
    }
}
