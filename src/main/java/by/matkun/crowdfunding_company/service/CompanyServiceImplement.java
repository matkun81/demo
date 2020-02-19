package by.matkun.crowdfunding_company.service;

import by.matkun.crowdfunding_company.dao.BonusRepository;
import by.matkun.crowdfunding_company.dao.CompanyRepository;
import by.matkun.crowdfunding_company.model.Bonus;
import by.matkun.crowdfunding_company.model.Company;
import by.matkun.crowdfunding_company.model.Tag;
import by.matkun.crowdfunding_company.model.User;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.temporal.ChronoUnit;
import java.util.*;


@Service
@RequiredArgsConstructor
public class CompanyServiceImplement implements CompanyService {
    public static final String URL = "url";
    private final CompanyRepository companyRepository;
    private final TagServiceImplement tagService;
    private final BonusRepository bonusRepository;
    private final Cloudinary cloudinary;

    @Value("${upload.path}")
    private String uploadPath;
    @Value("${cloudinary.api.key}")
    private String apiKey;

    @Override
    public Company find(Long id) {
        return companyRepository.findById(id).orElse(null);
    }

    @Override
    public List<Company> findAll() {
        return companyRepository.findAll();
    }

    @Override
    public List<Company> sortByRate() {
        List<Company> list = companyRepository.findAll();
        Collections.sort(list, new Comparator<Company>() {
            public int compare(Company o1, Company o2) {
                return o2.getAvgRate().compareTo(o1.getAvgRate());
            }
        });
        return list;
    }

    @Override
    public List<Company> sortByDate() {
        DateFormat format = new SimpleDateFormat("dd.MM.yyyy");
        List<Company> list = companyRepository.findAll();
        Collections.sort(list, new Comparator<Company>() {
            @SneakyThrows
            public int compare(Company o1, Company o2) {
                Date date1 = format.parse(o1.getDateOfStart());
                Date date2 = format.parse(o2.getDateOfStart());
                return date2.compareTo(date1);
            }
        });
        return list;
    }

    @Override
    public List<Company> sortByTag(String tagText) {
        List<Company> companyList = new ArrayList<>();
        List<Tag> tags = tagService.findTag(tagText);
        for (Tag tag : tags) {
            companyList.addAll(tag.getCompanies());
        }
        return companyList;
    }

    @Override
    public Iterable<Company> sortCompany(String topic, String tagText, String rate, String date) {
        if (topic != null) {
            return companyRepository.findCompaniesByTopic(topic);
        }
        if (tagText != null) {
            return sortByTag(tagText);
        }
        if (rate != null) {
            return sortByRate();
        }
        if (date != null) {
            return sortByDate();
        }
        return companyRepository.findAll();
    }

    @Override
    public Company save(Company company, User user, Set<Tag> tagSet) {
        company.setOwner(user);
        company.setTags(tagSet);
        return companyRepository.save(company);
    }

    @Override
    public Company update(Long id, Company company) {
        company.setId(id);
        return companyRepository.save(company);
    }

    @Override
    public Company deleteEasily(Company company) {
        company.setActivityTable(false);
        return companyRepository.save(company);
    }

    @Override
    public void delete(Long id) {
        companyRepository.deleteById(id);
    }

    @Transactional
    @Override
    public String donate(Company company, User user, Long bonusId) {
        Map<User, Float> mapDonates = company.getUsersDonates();
        Bonus bonus = bonusRepository.getOne(bonusId);
        if (mapDonates.containsKey(user)) {
            float money = mapDonates.get(user);
            money += bonus.getSumOfMoney();
            mapDonates.put(user, money);
        }else {
            mapDonates.put(user, bonus.getSumOfMoney());
        }
        companyRepository.save(company);
        return "successfully";
    }

    @Override
    public float getCurrentSumDonates(Company company) {
        float sum = 0;
        for (Map.Entry<User, Float> item : company.getUsersDonates().entrySet()) {
            sum += item.getValue();
        }
        return sum;
    }

    @Override
    public void uploadImage(Company company, MultipartFile file) throws IOException {
        if (file != null && !file.getOriginalFilename().isEmpty()) {
            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists()) {
                uploadDir.mkdir();
            }
            file.transferTo(new File(uploadPath + "/" + file.getOriginalFilename()));
            Map uploadResult = cloudinary.uploader().upload(uploadPath + "/" + file.getOriginalFilename(), ObjectUtils.emptyMap());
            for (File myFile : Objects.requireNonNull(new File(uploadPath).listFiles())) {
                if (myFile.isFile()) {
                    myFile.delete();
                }
            }
            String url = String.valueOf(uploadResult.get(URL));
            company.setFileName(url);
        }
    }

    @Override
    public String calculateAvgRate(Company company) {
        float sum = 0;
        for (Map.Entry<User, Float> item : company.getUsersRate().entrySet()) {
            sum += item.getValue();
        }
        return String.valueOf(sum / company.getUsersRate().size());
    }

    @Override
    public void addRate(Company company, User user, float rate) {
        if (!company.getUsersRate().containsKey(user)) {
            company.getUsersRate().put(user, rate);
            companyRepository.save(company);
        }
    }

    @Override
    public String getDurationCompany(Company company) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");
        Date firsDate = format.parse(company.getDateOfFinishing());
        Date secondDate = format.parse(company.getDateOfStart());
        return String.valueOf(ChronoUnit.DAYS.between(secondDate.toInstant(), firsDate.toInstant()));
    }


}
