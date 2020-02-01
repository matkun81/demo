package by.matkun.crowdfunding_company.dao;

import by.matkun.crowdfunding_company.model.Company;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyRepository extends JpaRepository<Company,Long> {
}
