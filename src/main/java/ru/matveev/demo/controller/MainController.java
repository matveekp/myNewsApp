package ru.matveev.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ru.matveev.demo.entity.RssBean;
import ru.matveev.demo.repositories.RssBeanRepository;

@Controller
public class MainController {
    @Autowired
    RssBeanRepository rssBeanRepository;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String startForm(@ModelAttribute RssBean rssBean, Model model) {
        model.addAttribute("top10", rssBeanRepository.findLast10());
        return "index";
    }

    @RequestMapping(value = "/allNews", method = RequestMethod.GET)
    public String allNews(@ModelAttribute RssBean rssBean, Model model) {
        model.addAttribute("allNews", rssBeanRepository.findAllByDate());
        return "allNews";
    }




}
