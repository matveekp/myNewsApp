package ru.matveev.demo.parser;


import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import ru.matveev.demo.service.HibernateSearchService;

import java.util.*;

@EnableScheduling //запуск по расписанию
@Component //объект создается автоматически
public class Parser {

    @Autowired //свойство создается автматом
    private ParserConfig config;

    @Autowired
    private HibernateSearchService hibernateSearchService;

    @Autowired
    private TaskExecutor taskExecutor;

    @Autowired
    private ApplicationContext context; // получить все объекты, которые там созданы

    private boolean notFirstTimeIndexing;

    private Set<String> links = Collections.synchronizedSet(new HashSet<>());

    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(Parser.class);

    public Set<String> getLinks() {
        return links;
    }

    public void setLinks(Set<String> links) {
        this.links = links;
    }

    @Scheduled(fixedRate = 300000)
    //@Bean //запустится один раз, вместо этого делаем @EnableScheduling + @Scheduled у метода
    public void start() {
        try {
            for (String rssUrl : config.getUrls()) {
                ParserThread thread = context.getBean(ParserThread.class);
                thread.setLink(rssUrl);
                taskExecutor.execute(thread);
            }
        } catch (Exception e) {
            LOGGER.error(e.toString());
        }

        // если это первый запуск, то будет ошибка NullPointerException.
        // Поэтому пауза перед началом индексирования должна быть дольше чем при последующих запусках
        try {
            if (notFirstTimeIndexing) {
                Thread.sleep(15000);
                hibernateSearchService.indexing();
            } else {
                Thread.sleep(30000);
                hibernateSearchService.indexing();
                notFirstTimeIndexing = true;
            }
        } catch (Exception e) {
            LOGGER.error(e.toString());
        }


    }


}





