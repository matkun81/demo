package by.matkun.crowdfunding_company.service;

import by.matkun.crowdfunding_company.model.Company;
import by.matkun.crowdfunding_company.model.User;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

public interface CompanyService {
    Company find(Long id);

    List<Company> findAll();

    Company save(Company company);

    void delete (Long id);

    void uploadImage(Company company, MultipartFile file) throws IOException;

    String calculateAvgRate(Company company, User user, float rate);

    String calculateRestOfDays(String  dateOfFinishing, String dateOfStarting) throws ParseException;

}
