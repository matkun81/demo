package by.matkun.crowdfunding_company.dao;

import by.matkun.crowdfunding_company.model.Bonus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BonusRepository extends JpaRepository<Bonus,Long> {
}
