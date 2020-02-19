package by.matkun.crowdfunding_company.service;

import by.matkun.crowdfunding_company.model.Company;
import by.matkun.crowdfunding_company.model.Tag;

import java.util.List;
import java.util.Set;

public interface TagService {
    Tag find(Long id);

    Set<Tag> findAll();

    Tag save(Tag tag);

    void delete (Long id);

    List<Tag> findTag(String text);

    Set<Tag> getTagFromString(String str, Company company);
}
