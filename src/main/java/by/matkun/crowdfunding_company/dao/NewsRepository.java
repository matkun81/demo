package by.matkun.crowdfunding_company.dao;

import by.matkun.crowdfunding_company.model.News;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.GregorianCalendar;
import java.util.List;

public interface NewsRepository extends JpaRepository<News,Long> {
}
