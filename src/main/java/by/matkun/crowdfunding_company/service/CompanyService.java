package by.matkun.crowdfunding_company.service;

import by.matkun.crowdfunding_company.model.Company;
import by.matkun.crowdfunding_company.model.Tag;
import by.matkun.crowdfunding_company.model.User;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;
import java.util.Set;

public interface CompanyService {
    Company find(Long id);

    List<Company> findAll();

    List<Company> sortByRate();

    List<Company> sortByDate();

    List<Company> sortByTag(String tagText);

    Iterable<Company> sortCompany(String topic,String tag,String rate,String date);

    Company save(Company company, User user,Set<Tag> tagSet);

    Company update(Long id,Company company);

    Company deleteEasily(Company company);

    void delete (Long id);

    String donate(Company company,User user,Long bonusId);

    float getCurrentSumDonates(Company company);

    void uploadImage(Company company, MultipartFile file) throws IOException;

    String calculateAvgRate(Company company);

    void addRate(Company company, User user, float rate);

    String getDurationCompany(Company company) throws ParseException;

}
