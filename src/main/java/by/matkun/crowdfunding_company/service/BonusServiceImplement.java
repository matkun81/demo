package by.matkun.crowdfunding_company.service;

import by.matkun.crowdfunding_company.dao.BonusRepository;
import by.matkun.crowdfunding_company.model.Bonus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BonusServiceImplement implements BonusService {
    @Autowired
    private BonusRepository bonusRepository;
    @Override
    public Bonus find(Long id) {
        return bonusRepository.findById(id).orElse(null);
    }

    @Override
    public List<Bonus> findAll() {
        return bonusRepository.findAll();
    }

    @Override
    public Bonus save(Bonus bonus) {
        return bonusRepository.save(bonus);
    }

    @Override
    public void delete(Long id) {
        bonusRepository.deleteById(id);

    }
}
