package by.matkun.crowdfunding_company.service;

import by.matkun.crowdfunding_company.dao.TagRepository;
import by.matkun.crowdfunding_company.model.Company;
import by.matkun.crowdfunding_company.model.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class TagServiceImplement implements TagService {

    @Autowired
    private TagRepository tagRepository;

    @Override
    public Tag find(Long id) {
        return tagRepository.findById(id).orElse(null);
    }

    @Override
    public Set<Tag> findAll() {
        return new HashSet<>(tagRepository.findAll());
    }
    @Override
    public Tag save(Tag tag) {
        return tagRepository.save(tag);
    }

    @Override
    public void delete(Long id) {
        tagRepository.deleteById(id);
    }

    @Override
    public List<Tag> findTag(String text) {
        return tagRepository.findByText(text);
    }

    @Override
    public Set<Tag> getTagFromString(String str, Company company) {
        Set<Tag> bufSet = new HashSet<>();
        String [] subStr;
        String deliMetr = ",";
        subStr = str.split(deliMetr);
        for (int i =0;i<subStr.length;i++){
        Tag tag = new Tag(subStr[i]);
            tagRepository.save(tag);
            bufSet.add(tag);
        }
        return bufSet;
    }
    public void saveTag(Company company){
       Set<Tag> setTag= company.getTags();
       for (Tag tag: setTag){
         Set<Company> companySet = tag.getCompanies();
         companySet.add(company);
         tag.setCompanies(companySet);
           tagRepository.save(tag);
       }
    }
}
