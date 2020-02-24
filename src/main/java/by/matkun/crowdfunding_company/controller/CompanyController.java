package by.matkun.crowdfunding_company.controller;

import by.matkun.crowdfunding_company.model.Company;
import by.matkun.crowdfunding_company.model.Topic;
import by.matkun.crowdfunding_company.model.User;
import by.matkun.crowdfunding_company.service.CompanyServiceImplement;
import by.matkun.crowdfunding_company.service.TagServiceImplement;
import by.matkun.crowdfunding_company.service.UserServiceImplement;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.security.Principal;

@Controller
@RequiredArgsConstructor
@RequestMapping("user/{userId}")
@PreAuthorize("hasAuthority('USER') or hasAuthority('ADMIN')")
public class CompanyController {

    private final CompanyServiceImplement companyService;
    private final UserServiceImplement userService;
    private final TagServiceImplement tagService;

    @GetMapping
    public String getListCompany(Principal principal, @PathVariable(name = "userId") User user, Model model) {
        User currentUser = userService.findAuthorizedUser(principal);
        model.addAttribute("listCompany", user.getCompanies());
        model.addAttribute("isCurrentUser", companyService.checkUserRights(currentUser, user));
        model.addAttribute("currentUser", user);
        model.addAttribute("topics", Topic.values());
        return "user";
    }

    @PostMapping
    public String createNewCompany(@Valid Company company, @RequestParam String tag, @RequestParam("file") MultipartFile file,
                                   @PathVariable(name = "userId") User user) throws IOException {
        companyService.uploadImage(company, file);
        companyService.save(company, user, tagService.getTagFromString(tag, company));
        tagService.saveTag(company);
        return "redirect:/user/{userId}";
    }

    @GetMapping("/editCompany/{companyId}")
    public String editCompany(@PathVariable(name = "companyId") Long companyId, @PathVariable(name = "userId") User user, Model model) {
        model.addAttribute("company", companyService.find(companyId));
        model.addAttribute("currentUser", user);
        model.addAttribute("topics", Topic.values());
        return "editCompany";
    }

    @PostMapping("/editCompany/{companyId}")
    public String updateCompany(Company company,
                                @PathVariable(name = "companyId") Long companyId,
                                @RequestParam Long owner,
                                @RequestParam("file") MultipartFile file) throws IOException {
        companyService.uploadImage(company, file);
        companyService.update(companyId, company);
        return "redirect:/user/" + owner;
    }

    @PostMapping("/deleteCompany")
    public String deleteCompany(@RequestParam Long companyId, @PathVariable(name = "userId") User user) {
            companyService.deleteEasily(companyService.find(companyId));
        return "redirect:/user/" + user.getId();
    }
}
