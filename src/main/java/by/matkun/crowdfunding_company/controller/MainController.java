package by.matkun.crowdfunding_company.controller;

import by.matkun.crowdfunding_company.model.*;
import by.matkun.crowdfunding_company.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.text.ParseException;
import java.util.List;

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
    private CommentServiceImplement commentService;

    @Autowired
    private NewsServiceImplement newsService;

    @GetMapping("/test")
    public String any(){

        return "test";
    }
    @GetMapping
    public String getListCompany(Principal principal, Model model){
        model.addAttribute("companyList",companyService.findAll());
        model.addAttribute("currentUser",userService.findAuthorizedUser(principal));
        return "main";
    }
    @GetMapping("company/{companyId}")
    public String getCompany(Principal principal,@PathVariable (name = "companyId")Company company, Model model) throws ParseException {
        model.addAttribute("currentUser", userService.findAuthorizedUser(principal));
        model.addAttribute("currentCompany", company);
        model.addAttribute("currentNews",company.getListNews());
        model.addAttribute("comments",company.getListComment());
        model.addAttribute("countDays",companyService.getDurationCompany(company));
        return "companyPage";
    }
    @GetMapping("content/listComment/{companyId}")
    public String getComments(@PathVariable (name = "companyId") Company company,Model model){
        model.addAttribute("comments",company.getListComment());
        return "content :: listComment";
    }

    @GetMapping("company/{companyId}/news/{newsId}")
    public String getNews(Principal principal,@PathVariable (name = "newsId")News news, Model model){
        model.addAttribute("currentUser", userService.findAuthorizedUser(principal));
        model.addAttribute("currentNews",newsService.find(news.getId()));
        return "newsViewPage";
    }

    @GetMapping("company/news/{newsId}")
    public String getByDateNews(Principal principal,Model model,@PathVariable(name ="newsId") News news){
        model.addAttribute("currentUser", userService.findAuthorizedUser(principal));
        model.addAttribute("listNews",newsService.findByDate(news));
        return "sortByDateNews";
    }
    @PostMapping("company/{companyId}")
    public String addRating(Principal principal,@PathVariable (name = "companyId") Company company,@RequestParam String avgRate, Model model){
        User user = userService.findAuthorizedUser(principal);
        if (user==null){
            model.addAttribute("warningMessage","You have already set rate to this company");
        }else {
                company.setAvgRate(Float.parseFloat(companyService.getAvgRate(company, user, Float.parseFloat(avgRate))));
                companyService.save(company);
            }
        return "redirect:/company/{companyId}";
    }
    @PostMapping("company/{companyId}/comments")
    public String addComments(Comment comment, @PathVariable (name = "companyId") Company company){
        comment.setCompany(company);
        commentService.save(comment);
        return "redirect:/company/{companyId}";
    }
    @PostMapping("company/{companyId}/donate")
    public String donate(Principal principal, Bonus bonus, @PathVariable (name = "companyId") Company company){
        User user = userService.findAuthorizedUser(principal);
        Bonus currentBonus = bonusService.find(bonus.getId());
        userService.addBonusToUser(user,currentBonus);
        userService.save(user);
        company.setCurrentSum(company.getCurrentSum()+currentBonus.getSumOfMoney());
        companyService.save(company);
        return "redirect:/company/{companyId}";
    }
}
