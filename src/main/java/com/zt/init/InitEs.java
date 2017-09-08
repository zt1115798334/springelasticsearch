package com.zt.init;

import com.zt.constants.SysConst;
import com.zt.es.service.EsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class InitEs implements InitializingBean {

    protected final Logger logger = LoggerFactory.getLogger(InitEs.class);

    @Autowired
    private EsService esService;

    @Override
    public void afterPropertiesSet() throws Exception {
        logger.info("初始化数据--创建索引");
        boolean flag = esService.isExistsIndex(SysConst.INDEX);
        if (!flag) {
            esService.createCluterName(SysConst.INDEX);
            esService.createMapping(SysConst.INDEX, SysConst.ARTICLE);
        }
    }
}
