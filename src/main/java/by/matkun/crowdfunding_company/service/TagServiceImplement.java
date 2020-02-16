package by.matkun.crowdfunding_company.service;

import by.matkun.crowdfunding_company.dao.TagRepository;
import by.matkun.crowdfunding_company.model.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class TagServiceImplement implements TagService {
    @Autowired
    private TagRepository tagRepository;

    @Override
    public Tag find(Long id) {
        return tagRepository.findById(id).orElse(null);
    }

    @Override
    public List<Tag> findAll() {
        return tagRepository.findAll();
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
    public Set<Tag> getTagFromString(String str) {
        Set<Tag> bufSet = new HashSet<>();
        String [] subStr;
        String deliMetr = ",";

        subStr = str.split(deliMetr);
        for (int i =0;i<subStr.length;i++){
        Tag tag = tagRepository.save(new Tag(subStr[i]));
            bufSet.add(tag);
        }
        return bufSet;
    }
}
