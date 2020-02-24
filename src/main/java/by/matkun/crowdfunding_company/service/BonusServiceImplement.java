package by.matkun.crowdfunding_company.service;

import by.matkun.crowdfunding_company.dao.BonusRepository;
import by.matkun.crowdfunding_company.model.Bonus;
import by.matkun.crowdfunding_company.model.Company;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BonusServiceImplement implements BonusService {

    private final CompanyServiceImplement companyService;
    private final BonusRepository bonusRepository;

    @Override
    public Bonus find(Long id) {
        return bonusRepository.findById(id).orElse(null);
    }

    @Override
    public List<Bonus> findAll() {
        return bonusRepository.findAll();
    }

    @Override
    public Bonus save(Bonus bonus, Company company) {
        bonus.setCompany(company);
        return bonusRepository.save(bonus);
    }

    @Override
    public List<Bonus> listBonusesOfCompany(Long companyId) {
        return companyService.find(companyId).getListBonus();
    }

    @Override
    public Bonus updateBonus(Long id, Bonus bonus) {
        bonus.setId(id);
        return bonusRepository.save(bonus);
    }

    @Override
    public void delete(Long id) {
        bonusRepository.deleteById(id);
    }

    @Override
    public Bonus deleteEasily(Long bonusId) {
        Bonus bonus = bonusRepository.getOne(bonusId);
        bonus.setActivity(false);
        return bonusRepository.save(bonus);
    }
}
