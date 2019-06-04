package ru.matveev.demo.parser;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.*;

@EnableScheduling //запуск по расписанию
@Component //объект создается автоматически
public class Parser {

    Set<String> links = Collections.synchronizedSet(new HashSet<>());

    List<String> urls = new ArrayList<>();

    public Set<String> getLinks() {
        return links;
    }

    public void setLinks(Set<String> links) {
        this.links = links;
    }

    @Autowired
    private TaskExecutor taskExecutor;

    @Autowired
    private ApplicationContext context; // получить все объекты, которые там созданы

    @Scheduled(fixedRate = 60000)
    //@Bean //запустится один раз, вместо этого делаем @EnableScheduling + @Scheduled у метода
    public void start() throws IOException {


        urls.add("http://rss.garant.ru/news/");
        urls.add("http://www.vesti.ru/vesti.rss");
        urls.add("http://www.vsesmi.ru/rss/19/");
        urls.add("https://news.yandex.ru/index.rss");


        for (String rssUrl : urls) {
            ParserThread thread = context.getBean(ParserThread.class);
            thread.setLink(rssUrl);
            taskExecutor.execute(thread);
        }
    }


}





