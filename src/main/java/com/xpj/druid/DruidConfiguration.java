package com.xpj.druid;

import com.alibaba.druid.pool.DruidDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

@Configuration
public class DruidConfiguration {
    private Logger logger = LoggerFactory.getLogger(DruidConfiguration.class);

    @Bean(destroyMethod = "close")
    @Primary
    @ConfigurationProperties(prefix = "spring.datasource")
    public DataSource druidDataSource() {
        try {
            return new DruidDataSource();
        } catch (Exception e) {
            logger.error("设置数据源为druid时出错：" + e.getMessage());
            return null;
        }
    }
}
