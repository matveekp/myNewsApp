package ru.matveev.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.matveev.demo.entity.RssBean;
import ru.matveev.demo.repositories.RssBeanRepository;

@RestController
public class NewsController {

    @Autowired
    private RssBeanRepository rssBeanRepository;

    @GetMapping("/rest_allNews") // описанный запрос будет приходить методом get
    Iterable<RssBean> findAll() {
        return rssBeanRepository.findAllByDate();
    }


}
