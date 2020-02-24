package by.matkun.crowdfunding_company.service;

import by.matkun.crowdfunding_company.dao.NewsRepository;
import by.matkun.crowdfunding_company.model.Company;
import by.matkun.crowdfunding_company.model.News;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NewsServiceImplement implements NewsService {
    private final NewsRepository newsRepository;

    @Override
    public News find(Long id) {
        return newsRepository.findById(id).orElse(null);
    }

    @Override
    public List<News> findAll() {
        return newsRepository.findAll();
    }

    @Override
    public News save(News news) {
        return newsRepository.save(news);
    }

    @Override
    public void delete(Long id) {
        newsRepository.deleteById(id);
    }

    @Override
    public List<News> findByDate(News news) {
        int day = news.getDateCreating().getTime().getDay();
        int month = news.getDateCreating().getTime().getMonth();
        List<News> listNewsByDate = new ArrayList<>();
        List<News> listNewsFromDb = newsRepository.findAll();
        for (News i: listNewsFromDb) {
            if (i.getDateCreating().getTime().getDay()==day && i.getDateCreating().getTime().getMonth()==month){
                listNewsByDate.add(i);
            }
        }
        return listNewsByDate;
    }

    @Override
    public News create(News news, Company company) {
        news.setCompany(company);
        return newsRepository.save(news);
    }

    @Override
    public News update(News news, Long id) {
        news.setId(id);
        return newsRepository.save(news);
    }
}
