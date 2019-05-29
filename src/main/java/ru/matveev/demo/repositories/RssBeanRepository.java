package ru.matveev.demo.repositories;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import ru.matveev.demo.entity.RssBean;

import java.util.Optional;

public interface RssBeanRepository extends CrudRepository<RssBean, Integer> {

    @Query("SELECT b FROM RssBean b order by b.newsDate")
    Optional<RssBean> findAllByDate();


}
