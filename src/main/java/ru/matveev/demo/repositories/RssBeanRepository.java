package ru.matveev.demo.repositories;

import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.repository.query.QueryByExampleExecutor;
import ru.matveev.demo.entity.RssBean;


public interface RssBeanRepository extends CrudRepository<RssBean, Integer> {

    @Query("SELECT b FROM RssBean b order by b.newsDate desc")
    Iterable<RssBean> findAllByDate();

    @Query(nativeQuery = true, value = "SELECT * FROM rss_bean  order by news_date desc limit 10")
    Iterable<RssBean> findLast10();

    @Query("SELECT b FROM RssBean b where b.rating = :rating ")
    Iterable<RssBean> findByRating(@Param("rating") Integer rating);

    @Query("SELECT b FROM RssBean b where b.source = :source ")
    Iterable<RssBean> findBySource(@Param("source") String source);

    @Query("SELECT b FROM RssBean b where b.rating = :rating and b.source = :source ")
    Iterable<RssBean> findByAdvance(@Param("rating") Integer rating , @Param("source") String source);




}
