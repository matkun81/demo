package by.matkun.crowdfunding_company.service;

import by.matkun.crowdfunding_company.dao.NewsRepository;
import by.matkun.crowdfunding_company.model.Company;
import by.matkun.crowdfunding_company.model.News;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

@Service
public class NewsServiceImplement implements NewsService {
    @Autowired
    private NewsRepository newsRepository;

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
}
