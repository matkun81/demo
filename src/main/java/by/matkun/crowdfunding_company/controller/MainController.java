package by.matkun.crowdfunding_company.controller;

import by.matkun.crowdfunding_company.model.*;
import by.matkun.crowdfunding_company.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.text.ParseException;

@Controller
@RequestMapping("/")
public class MainController {

    @Autowired
    private UserServiceImplement userService;

    @Autowired
    private CompanyServiceImplement companyService;

    @Autowired
    private BonusServiceImplement bonusService;

    @Autowired
    private NewsServiceImplement newsService;

    @Autowired
    private TagServiceImplement tagService;

    @GetMapping("/test")
    public String any() {

        return "test";
    }

    @GetMapping
    public String getListCompany(Principal principal, @RequestParam(required = false) String topic,
                                 @RequestParam(required = false) String tag,
                                 @RequestParam(required = false) String rate,
                                 @RequestParam(required = false) String date, Model model) {
        model.addAttribute("tags", tagService.findAll());
        model.addAttribute("companyList", companyService.sortCompany(topic, tag, rate, date));
        model.addAttribute("currentUser", userService.findAuthorizedUser(principal));
        model.addAttribute("topics", Topic.values());
        return "main";
    }

    @GetMapping("company/{companyId}")
    public String getCompany(Principal principal, @PathVariable(name = "companyId") Company company, Model model) throws ParseException {
        model.addAttribute("currentUser", userService.findAuthorizedUser(principal));
        model.addAttribute("currentCompany", company);
        model.addAttribute("currentNews", company.getListNews());
        model.addAttribute("comments", company.getListComment());
        model.addAttribute("countDays", companyService.getDurationCompany(company));
        model.addAttribute("avgRate", companyService.calculateAvgRate(company));
        model.addAttribute("currentSumOfDonates", companyService.getCurrentSumDonates(company));
        return "companyPage";
    }

    @GetMapping("company/{companyId}/news/{newsId}")
    public String getNews(Principal principal, @PathVariable(name = "newsId") News news, Model model) {
        model.addAttribute("currentUser", userService.findAuthorizedUser(principal));
        model.addAttribute("currentNews", newsService.find(news.getId()));
        return "newsViewPage";
    }

    @GetMapping("company/news/{newsId}")
    public String getByDateNews(Principal principal, Model model, @PathVariable(name = "newsId") News news) {
        model.addAttribute("currentUser", userService.findAuthorizedUser(principal));
        model.addAttribute("listNews", newsService.findByDate(news));
        return "sortByDateNews";
    }

    @PostMapping("company/{companyId}")
    public String addRating(Principal principal, @PathVariable(name = "companyId") Company company, @RequestParam String avgRate, Model model) {
        User user = userService.findAuthorizedUser(principal);
        if (user == null) {
            model.addAttribute("warningMessage", "You have already set rate to this company");
        } else {
            companyService.addRate(company, user, Float.parseFloat(avgRate));
        }
        return "redirect:/company/{companyId}";
    }

    @PostMapping("company/{companyId}/donate")
    public String donate(Principal principal, Bonus bonus, @PathVariable(name = "companyId") Company company) {
        User user = userService.findAuthorizedUser(principal);
        Bonus currentBonus = bonusService.find(bonus.getId());
        userService.addBonusToUser(user, currentBonus);
        userService.save(user);
        companyService.donate(company, user, bonus.getId());
        return "redirect:/company/{companyId}";
    }
}
