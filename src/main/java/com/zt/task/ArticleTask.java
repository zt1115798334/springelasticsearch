package com.zt.task;

import com.zt.base.entity.PageResult;
import com.zt.constants.SysConst;
import com.zt.es.service.EsService;
import com.zt.mongo.entity.Article;
import com.zt.mongo.service.ArticleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadPoolExecutor;

@Component
public class ArticleTask {

    protected final Logger logger = LoggerFactory.getLogger(ArticleTask.class);

    @Autowired
    private ArticleService articleService;

    @Autowired
    private EsService esService;

    @Scheduled(fixedDelay = 3600000)//下一次和上一次执行时延时1小时后开始执行
    public void execute() {
        logger.info(LocalDateTime.now() + "开始执行添加文章方法");
        ThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(2);
        Integer pageNumber = 1;
        Integer pageSize = SysConst.DEFAULT_BATCH_SIZE;
        executor.submit(() -> {
            PageResult<Article> page = articleService.findListByPage(pageNumber, pageSize);
            long totalPages = page.getTotalPages();
            List<Article> articles = page.getList();
            esService.batchSave(SysConst.INDEX, SysConst.TYPE, articles);
            for (int i = 2; i <= totalPages; i++) {
                logger.info("页数 = " + i);
                List<Article> articles1 = articleService.findListByPageRtList(i, pageSize);
                esService.batchSave(SysConst.INDEX, SysConst.TYPE, articles1);
            }
        });

    }
}
