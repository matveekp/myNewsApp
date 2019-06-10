package ru.matveev.demo.service;

import org.apache.lucene.search.Query;
import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.Search;
import org.hibernate.search.query.dsl.QueryBuilder;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.matveev.demo.entity.RssBean;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import java.util.List;

@Service
public class HibernateSearchService {

    @Autowired
    EntityManager entityManager;

    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(HibernateSearchService.class);

    private FullTextEntityManager fullTextEntityManager;

    @Transactional
    public void indexing() {
            fullTextEntityManager.createIndexer().start();
    }

    @Transactional
    public List<RssBean> fuzzySearch(String searchTerm) {

        //извлекаем fullTextEntityManager, используя entityManager
        fullTextEntityManager = Search.getFullTextEntityManager(entityManager);

//        try {
//            fullTextEntityManager.createIndexer().startAndWait();
//        } catch (InterruptedException e) {
//            LOGGER.error(e.toString());
//        }

        // создаем запрос при помощи Hibernate Search query DSL
        QueryBuilder qb = fullTextEntityManager.getSearchFactory().buildQueryBuilder().forEntity(RssBean.class).get();

        //обозначаем поля, по которым необходимо произвести поиск
        //keyword () указывает, что мы ищем одно конкретное слово, onField () сообщает Lucene, где искать, а matching () что искать.
        Query luceneQuery = qb.keyword().fuzzy().withEditDistanceUpTo(1).withPrefixLength(1).onFields("title")
                .matching(searchTerm).createQuery();


        //оборачиваем Lucene Query в Hibernate Query object
        javax.persistence.Query jpaQuery = fullTextEntityManager.createFullTextQuery(luceneQuery, RssBean.class);

        // execute search

        List<RssBean> RssBeanList = null;
        try {
            RssBeanList = (List<RssBean>) jpaQuery.getResultList();
        } catch (NoResultException e) {
            LOGGER.error(e.toString());
        }

        return RssBeanList;
    }
}
