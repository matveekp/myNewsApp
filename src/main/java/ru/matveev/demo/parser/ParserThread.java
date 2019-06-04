package ru.matveev.demo.parser;

import com.rometools.rome.feed.synd.SyndContent;
import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.FeedException;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import ru.matveev.demo.entity.RssBean;
import ru.matveev.demo.repositories.RssBeanRepository;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;


@Component
@Scope("prototype") //без этого будет создан только один объект
public class ParserThread implements Runnable {


    @Autowired
    private RssBeanRepository rssBeanRepository;

    private Parser parser;
    private String link;


    public ParserThread(Parser parser) {
        this.parser = parser;
    }

    public void setLink(String link) {
        this.link = link;
    }

    @Override
    public void run() {

        try (XmlReader reader = new XmlReader(new URL(link))) {
            SyndFeed feed = new SyndFeedInput().build(reader);

            for (SyndEntry entry : feed.getEntries()) {
                if (!parser.getLinks().contains(entry.getUri())) {

                RssBean rss = new RssBean();
                SyndContent syndContent = entry.getDescription();

                    rss.setTitle(entry.getTitle());
                    rss.setDescription(syndContent.getValue());
//                    rss.setLink(entry.getLink());
                    rss.setNewsDate(convertToLocalDateViaInstant(entry.getPublishedDate()));
                    rss.setUrl(new URL(entry.getUri()));

                    parser.getLinks().add(entry.getUri());
                    rssBeanRepository.save(rss);

                    System.out.println(parser.getLinks().size());
                    System.out.println("added by " + Thread.currentThread().getName());
                }
            }
        } catch (FeedException | IOException e) {
            e.printStackTrace();
        }

    }


    public LocalDateTime convertToLocalDateViaInstant(Date dateToConvert) {
        return dateToConvert.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();
    }
}
