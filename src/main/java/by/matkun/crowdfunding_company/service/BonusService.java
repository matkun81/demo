package by.matkun.crowdfunding_company.service;

import by.matkun.crowdfunding_company.model.Bonus;
import by.matkun.crowdfunding_company.model.Company;

import java.util.List;

public interface BonusService {
    Bonus find(Long id);

    List<Bonus> findAll();

    Bonus save(Bonus bonus, Company company);

    List<Bonus> listBonusesOfCompany(Long companyId);

    Bonus updateBonus(Long id, Bonus bonus);

    void delete (Long id);

    Bonus deleteEasily(Long bonusId);
}
