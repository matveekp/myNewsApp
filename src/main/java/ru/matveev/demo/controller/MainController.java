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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Controller
public class MainController {
    @Autowired
    RssBeanRepository rssBeanRepository;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String startForm(@ModelAttribute RssBean rssBean, Model model) {

        List<String> urls = new ArrayList<>();
        urls.add("http://rss.garant.ru/news/");
        urls.add("http://www.vesti.ru/vesti.rss");
        urls.add("http://www.vsesmi.ru/rss/19/");


        for (String rssUrl : urls) {

            try (XmlReader reader = new XmlReader(new URL(rssUrl))) {
                SyndFeed feed = new SyndFeedInput().build(reader);

                for (SyndEntry entry : feed.getEntries()) {
                    RssBean rss = new RssBean();
                    SyndContent syndContent = entry.getDescription();

                    rss.setTitle(entry.getTitle());
                    rss.setDescription(syndContent.getValue());
//                    rss.setLink(entry.getLink());
                    rss.setNewsDate(convertToLocalDateViaInstant(entry.getPublishedDate()));
                    rss.setUrl(new URL(entry.getUri()));

                    rssBeanRepository.save(rss);

                }

            } catch (FeedException | IOException e) {
                e.printStackTrace();
            }

        }

        model.addAttribute("allNews", rssBeanRepository.findAll());

        return "index";
    }

    public LocalDateTime convertToLocalDateViaInstant(Date dateToConvert) {
        return dateToConvert.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();
    }

}
