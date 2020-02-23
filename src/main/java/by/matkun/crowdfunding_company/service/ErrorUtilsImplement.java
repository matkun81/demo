package by.matkun.crowdfunding_company.service;

import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

@Service
public class ErrorUtilsImplement implements ErrorUtils {
    @Override
    public String getErrors(BindingResult result,Model model) {
        if (result.hasErrors()){
            model.addAttribute("errors",result.getAllErrors());
            return "errorsPage";
        }
        return null;
    }
}
