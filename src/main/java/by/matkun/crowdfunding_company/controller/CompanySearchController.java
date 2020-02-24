package by.matkun.crowdfunding_company.controller;

import by.matkun.crowdfunding_company.service.HibernateSearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class CompanySearchController {

    private final HibernateSearchService hibernateSearchService;

    @GetMapping("search")
    public String search(@RequestParam String text, Model model) throws InterruptedException {
        model.addAttribute("companies", hibernateSearchService.fuzzySearch(text));
        return "companySearchResult";
    }
}
