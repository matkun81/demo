package by.matkun.crowdfunding_company.controller;

import by.matkun.crowdfunding_company.model.Bonus;
import by.matkun.crowdfunding_company.model.Company;
import by.matkun.crowdfunding_company.model.User;
import by.matkun.crowdfunding_company.service.BonusServiceImplement;
import by.matkun.crowdfunding_company.service.CompanyServiceImplement;
import by.matkun.crowdfunding_company.service.UserServiceImplement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/")
public class MainController {

    @Autowired
    private UserServiceImplement userServiceImplement;

    @Autowired
    private CompanyServiceImplement companyService;

    @Autowired
    private BonusServiceImplement bonusService;
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
    @GetMapping("company/{companyId}")
    public String getCompany(@PathVariable (name = "companyId")Company company, Model model){
        model.addAttribute("currentCompany", company);
        return "companyPage";
    }
    @PostMapping("company/{companyId}")
    public String addRating(@AuthenticationPrincipal User user,@PathVariable (name = "companyId") Long companyId,@RequestParam String avgRate, Model model){
        Company company = companyService.find(companyId);
        if (companyService.checkUsersLike(company, user) ||user==null){
            model.addAttribute("warningMessage","You have already set rate to this company");
        }else {
            List<User> userRate = company.getOwnerRate();
            userRate.add(user);
            company.setOwnerRate(userRate);
            company.setAvgRate(companyService.calculateAvgRate(Float.parseFloat(avgRate)));
            companyService.save(company);
        }
        return "redirect:/company/{companyId}";
    }
    @PostMapping("company/{companyId}/donate")
    public String donate(Bonus bonus, @PathVariable Long companyId,
                         @AuthenticationPrincipal User currentUser){
        User user = userServiceImplement.find(currentUser.getId());
        Bonus currentBonus = bonusService.find(bonus.getId());
        List<Bonus> listBonus = user.getBonusList();
        listBonus.add(currentBonus);
        user.setBonusList(listBonus);
        userServiceImplement.save(user);
        Company company = companyService.find(companyId);
        company.setCurrentSum(company.getCurrentSum()+currentBonus.getSumOfMoney());
        companyService.save(company);
        return "redirect:/company/{companyId}";
    }
}
