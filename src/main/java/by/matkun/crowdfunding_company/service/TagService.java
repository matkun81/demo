package by.matkun.crowdfunding_company.service;

import by.matkun.crowdfunding_company.model.Tag;

import java.util.List;
import java.util.Set;

public interface TagService {
    Tag find(Long id);

    List<Tag> findAll();

    Tag save(Tag tag);

    void delete (Long id);

    Set<Tag> getTagFromString(String str);
}
