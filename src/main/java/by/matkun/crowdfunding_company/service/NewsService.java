package by.matkun.crowdfunding_company.service;

import by.matkun.crowdfunding_company.model.Company;
import by.matkun.crowdfunding_company.model.News;

import java.util.List;

public interface NewsService {
    News find(Long id);

    List<News> findAll();

    News save(News news);

    void delete (Long id);

    List<News> findByDate(News news);

    News create(News news, Company company);

    News update(News news,Long id);
}
