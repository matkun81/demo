package by.matkun.crowdfunding_company.controller;

import by.matkun.crowdfunding_company.model.Bonus;
import by.matkun.crowdfunding_company.model.Company;
import by.matkun.crowdfunding_company.model.User;
import by.matkun.crowdfunding_company.service.BonusServiceImplement;
import by.matkun.crowdfunding_company.service.CompanyServiceImplement;
import by.matkun.crowdfunding_company.service.UserServiceImplement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/user/{userId}/company/{companyId}/bonus")
public class BonusController {

    @Autowired
    private CompanyServiceImplement companyService;

    @Autowired
    private BonusServiceImplement bonusService;

    @Autowired
    private UserServiceImplement userService;

    @GetMapping
    public String getBonuses(Principal principal, Model model, @PathVariable Long companyId, @PathVariable Long userId){
        List<Bonus> listBonuses = companyService.find(companyId).getListBonus();
        User currentUser = userService.findAuthorizedUser(principal);
        model.addAttribute("currentUser",currentUser);
        model.addAttribute("listBonuses", listBonuses);
        model.addAttribute("companyId",companyId);
        model.addAttribute("userId",userId);
        return "bonusPage";
    }
    @PostMapping
    public String createBonus(@PathVariable (name = "companyId") Company company, Bonus bonus){
        bonus.setCompany(company);
        bonusService.save(bonus);
        return "redirect:/user/{userId}/company/{companyId}/bonus";
    }
    @GetMapping("/editBonus/{bonusId}")
    public String editNews(Principal principal,@PathVariable Long bonusId,@PathVariable Long userId,@PathVariable Long companyId, Model model){
        Bonus currentBonus = bonusService.find(bonusId);
        User currentUser = userService.findAuthorizedUser(principal);
        model.addAttribute("currentUser",currentUser);
        model.addAttribute("currentBonus", currentBonus);
        model.addAttribute("userId", userId);
        model.addAttribute("companyId",companyId);
        return "editBonus";
    }

    @PostMapping("/editBonus/{bonusId}")
    public String updateNews(Bonus bonus, @PathVariable Long bonusId){
        bonus.setId(bonusId);
        bonusService.save(bonus);
        return "redirect:/user/{userId}/company/{companyId}/bonus";
    }
    @PostMapping("/deleteBonus")
    public String deleteBonus(@RequestParam Long bonusId){
        bonusService.delete(bonusId);
        return "redirect:/user/{userId}/company/{companyId}/bonus";
    }
}
