package ru.matveev.demo.controller;

import com.rometools.rome.feed.synd.SyndContent;
import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.FeedException;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import ru.matveev.demo.entity.RssBean;
import ru.matveev.demo.repositories.RssBeanRepository;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;


@Controller
public class MainController {
    @Autowired
    RssBeanRepository rssBeanRepository;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String startForm(@ModelAttribute RssBean rssBean, Model model) {
//        model.addAttribute("allNews", rssBeanRepository.findAllByDate());
        model.addAttribute("top10", rssBeanRepository.findLast10());
        return "index";
    }

    @RequestMapping(value = "/allNews", method = RequestMethod.GET)
    public String allNews(@ModelAttribute RssBean rssBean, Model model) {
//        model.addAttribute("allNews", rssBeanRepository.findAllByDate());
        model.addAttribute("allNews", rssBeanRepository.findAllByDate());
        return "allNews";
    }




}
