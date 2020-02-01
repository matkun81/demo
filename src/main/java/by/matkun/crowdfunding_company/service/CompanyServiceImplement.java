package by.matkun.crowdfunding_company.service;

import by.matkun.crowdfunding_company.dao.CompanyRepository;
import by.matkun.crowdfunding_company.model.Company;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;


@Service
public class CompanyServiceImplement implements CompanyService  {
    @Value("${upload.path}")
    private String uploadPath;

    @Autowired
    private CompanyRepository companyRepository;

    @Override
    public Company find(Long id) {
        return companyRepository.findById(id).orElse(null);
    }

    @Override
    public List<Company> findAll() {
        return companyRepository.findAll();
    }

    @Override
    public Company save(Company company) {
        return companyRepository.save(company);
    }

    @Override
    public void delete(Long id) {
        companyRepository.deleteById(id);
    }

    @Override
    public void uploadImage(Company company, MultipartFile file) throws IOException {

        if (file != null) {
            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists()) {
                uploadDir.mkdir();
            }
            String uuidFile = UUID.randomUUID().toString();
            String resultFileName = uuidFile + "." + file.getOriginalFilename();
            file.transferTo(new File(uploadPath + "/" + resultFileName));
            company.setFileName(resultFileName);
        }
    }
}
