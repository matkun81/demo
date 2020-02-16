package by.matkun.crowdfunding_company.dao;

import by.matkun.crowdfunding_company.model.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TagRepository extends JpaRepository<Tag,Long> {
}
