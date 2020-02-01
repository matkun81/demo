package by.matkun.crowdfunding_company.service;

import by.matkun.crowdfunding_company.model.Bonus;

import java.util.List;

public interface BonusService {
    Bonus find(Long id);

    List<Bonus> findAll();

    Bonus save(Bonus bonus);

    void delete (Long id);
}
