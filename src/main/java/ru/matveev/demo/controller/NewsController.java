package ru.matveev.demo.controller;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import ru.matveev.demo.entity.RssBean;
import ru.matveev.demo.repositories.RssBeanRepository;
import ru.matveev.demo.service.HibernateSearchService;

import java.util.List;

@RestController
public class NewsController {

    @Autowired
    private RssBeanRepository rssBeanRepository;

    @Autowired
    private HibernateSearchService searchService;

    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(NewsController.class);

    @GetMapping("/rest_allNews")
        // описанный запрос будет приходить методом get
    Iterable<RssBean> findAll() {
        return rssBeanRepository.findAllByDate();

    }

    @GetMapping("/rest_findNews/{title}")
    public List<RssBean> search(@PathVariable("title") String request) {
        List<RssBean> searchResults = null;
        try {

            searchResults = searchService.fuzzySearch(request);

        } catch (Exception e) {
            LOGGER.error(e.toString());
        }

        return searchResults;

    }
}
