package by.matkun.crowdfunding_company.controller;

import by.matkun.crowdfunding_company.model.Company;
import by.matkun.crowdfunding_company.model.Tag;
import by.matkun.crowdfunding_company.model.User;
import by.matkun.crowdfunding_company.service.CompanyServiceImplement;
import by.matkun.crowdfunding_company.service.TagServiceImplement;
import by.matkun.crowdfunding_company.service.UserServiceImplement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import javax.xml.crypto.Data;
import java.io.IOException;
import java.security.Principal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("user/{userId}")
public class UserController {

    @Autowired
    private CompanyServiceImplement companyService;

    @Autowired
    private UserServiceImplement userService;

    @Autowired
    private TagServiceImplement tagService;

    @GetMapping
    public String getListCompany(Principal principal, @PathVariable (name = "userId") User user, Model model){
        User currentUser = userService.findAuthorizedUser(principal);
        List<Company> listCompany = user.getCompanies();
        model.addAttribute("listCompany",listCompany);
        model.addAttribute("isCurrentUser",currentUser.equals(user));
        model.addAttribute("currentUser",user);
        return "user";
    }

    @PostMapping
    public String createNewCompany(@Valid Company company, @RequestParam String tag, @RequestParam("file") MultipartFile file,
                                   @PathVariable (name = "userId")User user, BindingResult bindingResult,Model model) throws IOException {
        Set<Tag> tagsFromString = tagService.getTagFromString(tag);
        company.setTags(tagsFromString);
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
    public String updateCompany(Company company,
                                @PathVariable(name = "companyId") Long companyId,
                                @RequestParam Long owner,
                                @RequestParam ("file") MultipartFile file) throws IOException{
        company.setId(companyId);
        companyService.uploadImage(company,file);
        companyService.save(company);
        return "redirect:/user/" + owner;
    }
    @PostMapping("/deleteCompany")
    public String deleteCompany(@RequestParam Long companyId, @PathVariable (name = "userId") User user){
        try {
            companyService.delete(companyId);
        } catch (Exception e){
            Company company = companyService.find(companyId);
            company.setActivityTable(false);
            companyService.save(company);
        }
        return "redirect:/user/" + user.getId() ;
    }
}
