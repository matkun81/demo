package by.matkun.crowdfunding_company.controller;

import by.matkun.crowdfunding_company.model.Bonus;
import by.matkun.crowdfunding_company.model.Company;
import by.matkun.crowdfunding_company.service.BonusServiceImplement;
import by.matkun.crowdfunding_company.service.ErrorUtilsImplement;
import by.matkun.crowdfunding_company.service.UserServiceImplement;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
@RequestMapping("/user/{userId}/company/{companyId}/bonus")
@PreAuthorize("hasAuthority('USER') or hasAuthority('ADMIN')")
public class BonusController {

    private final BonusServiceImplement bonusService;
    private final UserServiceImplement userService;

    @GetMapping
    public String getBonuses(Principal principal, @PathVariable Long companyId, @PathVariable Long userId, Model model) {
        model.addAttribute("currentUser", userService.findAuthorizedUser(principal));
        model.addAttribute("listBonuses", bonusService.listBonusesOfCompany(companyId));
        model.addAttribute("companyId", companyId);
        model.addAttribute("userId", userId);
        return "bonusPage";
    }

    @PostMapping
    public String createBonus(@PathVariable(name = "companyId") Company company, Bonus bonus) {
        bonusService.save(bonus, company);
        return "redirect:/user/{userId}/company/{companyId}/bonus";
    }

    @GetMapping("/editBonus/{bonusId}")
    public String editBonus(Principal principal, @PathVariable Long bonusId, @PathVariable Long userId, @PathVariable Long companyId, Model model) {
        model.addAttribute("currentUser", userService.findAuthorizedUser(principal));
        model.addAttribute("currentBonus", bonusService.find(bonusId));
        model.addAttribute("userId", userId);
        model.addAttribute("companyId", companyId);
        return "editBonus";
    }

    @PostMapping("/editBonus/{bonusId}")
    public String updateBonus(Bonus bonus, @PathVariable Long bonusId) {
        bonusService.updateBonus(bonusId, bonus);
        return "redirect:/user/{userId}/company/{companyId}/bonus";
    }

    @PostMapping("/deleteBonus")
    public String deleteBonus(@RequestParam Long bonusId) {
        bonusService.deleteEasily(bonusId);
        return "redirect:/user/{userId}/company/{companyId}/bonus";
    }
}
