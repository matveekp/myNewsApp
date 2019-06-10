package ru.matveev.demo.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import ru.matveev.demo.entity.RssBean;


public interface RssBeanRepository extends CrudRepository<RssBean, Integer> {

    @Query("SELECT b FROM RssBean b order by b.newsDate desc")
    Iterable<RssBean> findAllByDate();

    @Query(nativeQuery = true, value = "SELECT * FROM rss_bean  order by news_date desc limit 10")
    Iterable<RssBean> findLast10();

}
