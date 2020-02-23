package by.matkun.crowdfunding_company.controller;

import by.matkun.crowdfunding_company.model.Bonus;
import by.matkun.crowdfunding_company.model.Company;
import by.matkun.crowdfunding_company.service.BonusServiceImplement;
import by.matkun.crowdfunding_company.service.ErrorUtilsImplement;
import by.matkun.crowdfunding_company.service.UserServiceImplement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;

@Controller
@RequestMapping("/user/{userId}/company/{companyId}/bonus")
public class BonusController {

    private final BonusServiceImplement bonusService;
    private final UserServiceImplement userService;
    private final ErrorUtilsImplement errorUtil;

    public BonusController(BonusServiceImplement bonusService, UserServiceImplement userService, ErrorUtilsImplement errorUtil) {
        this.bonusService = bonusService;
        this.userService = userService;
        this.errorUtil = errorUtil;
    }

    @GetMapping
    public String getBonuses(Principal principal, @PathVariable Long companyId, @PathVariable Long userId, Model model){
            model.addAttribute("currentUser", userService.findAuthorizedUser(principal));
            model.addAttribute("listBonuses", bonusService.listBonusesOfCompany(companyId));
            model.addAttribute("companyId", companyId);
            model.addAttribute("userId", userId);
        return "bonusPage";
    }
    @PostMapping
    public String createBonus(@PathVariable (name = "companyId") Company company, Bonus bonus){
            bonusService.save(bonus, company);
        return "redirect:/user/{userId}/company/{companyId}/bonus";
    }
    @GetMapping("/editBonus/{bonusId}")
    public String editBonus(Principal principal,@PathVariable Long bonusId,@PathVariable Long userId,@PathVariable Long companyId, Model model){
        model.addAttribute("currentUser",userService.findAuthorizedUser(principal));
        model.addAttribute("currentBonus", bonusService.find(bonusId));
        model.addAttribute("userId", userId);
        model.addAttribute("companyId",companyId);
        return "editBonus";
    }

    @PostMapping("/editBonus/{bonusId}")
    public String updateBonus(Bonus bonus, @PathVariable Long bonusId,Model model){
        bonusService.updateBonus(bonusId,bonus);
        return "redirect:/user/{userId}/company/{companyId}/bonus";
    }
    @PostMapping("/deleteBonus")
    public String deleteBonus(@RequestParam Long bonusId){
        bonusService.deleteEasily(bonusId);
        return "redirect:/user/{userId}/company/{companyId}/bonus";
    }
}
