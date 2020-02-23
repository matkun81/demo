package by.matkun.crowdfunding_company.service;

import by.matkun.crowdfunding_company.model.Company;
import org.apache.lucene.search.Query;
import org.hibernate.search.exception.EmptyQueryException;
import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.Search;
import org.hibernate.search.query.dsl.QueryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import java.util.List;

@Service
public class HibernateSearchService {

    @Autowired
    private final EntityManager entityManager;


    @Autowired
    public HibernateSearchService(EntityManager entityManager) {
        super();
        this.entityManager = entityManager;
    }


    public void initializeHibernateSearch() {

        try {
            FullTextEntityManager fullTextEntityManager = Search.getFullTextEntityManager(entityManager);
            fullTextEntityManager.createIndexer().startAndWait();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Transactional
    public List<Company> fuzzySearch(String searchTerm) {
        FullTextEntityManager fullTextEntityManager = Search.getFullTextEntityManager(entityManager);
        QueryBuilder qb = fullTextEntityManager
                .getSearchFactory()
                .buildQueryBuilder()
                .forEntity(Company.class).get();

        Query luceneQuery;
        try {

                luceneQuery = qb
                    .keyword()
                    .fuzzy()
                    .withEditDistanceUpTo(2)
                    .onFields("name", "description", "listBonus.name", "listBonus.description", "listNews.title", "listNews.text")
                    .matching(searchTerm)
                    .createQuery();
        }catch (EmptyQueryException e){
            return null;
        }
        javax.persistence.Query jpaQuery = fullTextEntityManager.createFullTextQuery(luceneQuery, Company.class);
        List<Company> companyList = null;
        try {
            companyList = jpaQuery.getResultList();
        } catch (NoResultException nre) {
        }
        return companyList;


    }
}
