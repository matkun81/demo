package by.matkun.crowdfunding_company.service;

import by.matkun.crowdfunding_company.model.Company;
import by.matkun.crowdfunding_company.model.User;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface CompanyService {
    Company find(Long id);

    List<Company> findAll();

    Company save(Company company);

    void delete (Long id);

    void uploadImage(Company company, MultipartFile file) throws IOException;

    float calculateAvgRate(float rate);

    boolean checkUsersLike(Company company,User user);
}
