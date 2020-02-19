package by.matkun.crowdfunding_company.dao;

import by.matkun.crowdfunding_company.model.Company;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CompanyRepository extends JpaRepository<Company,Long> {
    List<Company> findCompaniesByTopic(String topic);
}
