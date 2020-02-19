package by.matkun.crowdfunding_company.dao;

import by.matkun.crowdfunding_company.model.Company;
import by.matkun.crowdfunding_company.model.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Set;

public interface TagRepository extends JpaRepository<Tag,Long> {
    List<Tag> findByText(String text);
}
