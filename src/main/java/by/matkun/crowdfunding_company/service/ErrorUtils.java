package by.matkun.crowdfunding_company.service;

import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

public interface ErrorUtils{
    String getErrors(BindingResult result,Model model);
}
