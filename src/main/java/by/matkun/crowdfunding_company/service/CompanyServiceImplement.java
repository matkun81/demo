package by.matkun.crowdfunding_company.service;

import by.matkun.crowdfunding_company.dao.CompanyRepository;
import by.matkun.crowdfunding_company.model.Company;
import com.cloudinary.Cloudinary;
import com.cloudinary.Transformation;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.*;


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
        Map config = new HashMap();
        config.put("cloud_name", "matkun");
        config.put("api_key", "322761257467638");
        config.put("api_secret", "0Z0KmB0457_7s_bZO-w1WR27iYs");
        Cloudinary cloudinary = new Cloudinary(config);

        if (file != null && !file.getOriginalFilename().isEmpty()) {
            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists()) {
                uploadDir.mkdir();
            }
            file.transferTo(new File(uploadPath + "/" + file.getOriginalFilename()));
           Map uploadResult = cloudinary.uploader().upload(uploadPath + "/" + file.getOriginalFilename(),ObjectUtils.emptyMap());
            for (File myFile: Objects.requireNonNull(new File(uploadPath).listFiles())){
                if (myFile.isFile()) {
                    myFile.delete();
                }
            }
           String url = String.valueOf(uploadResult.get("url"));
            company.setFileName(url);
        }
    }
}
